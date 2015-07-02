/* 
 * The MIT License
 *
 * Copyright 2015 Apothesource, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.apothesource.pillfill.datamodel.android;

import com.apothesource.pillfill.datamodel.PatientType;
import com.apothesource.pillfill.datamodel.RxListType;
import com.apothesource.pillfill.datamodel.UserDataType;
import com.apothesource.pillfill.datamodel.android.exception.EncryptionException;
import com.apothesource.pillfill.datamodel.userdatatype.Credential;
import com.apothesource.pillfill.exception.PFMasterPasswordException;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.security.GeneralSecurityException;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.CryptorException;
import org.cryptonode.jncryptor.InvalidHMACException;
import org.cryptonode.jncryptor.JNCryptor;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.NoSuchPaddingException;

/**
 * This subclass of {@link PatientType} ensures that sensitive information is
 * stored in the encrypted {@link SecureUserDataBlock} DTO.
 * 
 * @author Apothesource, Inc
 */
public class SecurePatientTypeWrapper extends PatientType {

    private static final long serialVersionUID = 1L;
    private static final String TAG = "SecurePatientWrapper";
    private static final Logger log = Logger.getLogger(TAG);
    private transient Gson gson;
    private transient JNCryptor cryptor;

    /**
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public SecurePatientTypeWrapper() {
        super();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm a").create();
        try {
            init();
        } catch (GeneralSecurityException e) {
            log.log(Level.SEVERE, "Could not initialize crypto parameters.", e);
        }
    }

    /**
     * A helper method to ensure that duplicate references to prescriptions are
     * removed prior to serialization. It also sets metadata about the patient
     * to make enable the server to collect general metrics.
     * 
     * @param udt A {@link UserDataType} reference where metrics will be stored
     * @param rxList The list of prescriptions to be filtered.
     * @return The filtered prescription list 
     */
    private static RxListType checkRxListConsistency(UserDataType udt, RxListType rxList) {
        //Convert to hashsets and back to arraylists to filter duplicates
        rxList.setCurrentList(new ArrayList<String>(new HashSet<String>(rxList.getCurrentList())));
        rxList.setInactiveList(new ArrayList<String>(new HashSet<String>(rxList.getInactiveList())));
        rxList.setHiddenList(new ArrayList<String>(new HashSet<String>(rxList.getHiddenList())));

        if (rxList.getHiddenList().removeAll(rxList.getCurrentList()))
            log.log(Level.FINE, "Found duplicate RxIds between the hidden/active list. Removed.");
        if (rxList.getHiddenList().removeAll(rxList.getInactiveList()))
            log.log(Level.FINE, "Found duplicate RxIds between the hidden/inactive list. Removed.");
        if (rxList.getInactiveList().removeAll(rxList.getCurrentList()))
            log.log(Level.FINE, "Found duplicate RxIds between the active/inactive list. Removed.");

        udt.setTotalRxCount(rxList.getTotalListCount());
        return rxList;
    }

    private void init() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        cryptor = new AES256JNCryptor();
        if (userData == null) userData = new UserDataType();
    }

    /**
     * Convenience method to check if a provided password is correct
     * @param password The password to check
     * @return Whether the password enabled the decryption of the data
     */
    public boolean testPassword(char[] password) {
        try {
            if (password == null || password.length == 0) {
                log.log(Level.WARNING, "Null/empty password sent to testPassword.");
                return false;
            }
            byte[] secureBlock = BaseEncoding.base64().decode(getSecureUserDataBlockText());
            cryptor.decryptData(secureBlock, password);
            return true;
        } catch (CryptorException e) {
            log.log(Level.WARNING, "Password test failed: " + e.toString());
            return false;
        }

    }

    public void openPatientDocument(char[] password) throws PFMasterPasswordException {
        if (password == null || password.length == 0) {
            throw new PFMasterPasswordException("Password not set.", (getSecureUserDataBlockText() != null)); //If there's not a data block, then we're setting the password for the first time.
        }
        try {
            if (getSecureUserDataBlockText() != null) {
                byte[] secureBlock = BaseEncoding.base64().decode(getSecureUserDataBlockText());
                String unencryptedBlock = new String(cryptor.decryptData(secureBlock, password));
                SecureUserDataBlock sblock;
                try {
                    sblock = gson.fromJson(unencryptedBlock, SecureUserDataBlock.class);
                } catch (JsonSyntaxException jse) {
                    //TODO: Temporary hack to deal with inconsistent serialization between iOS and Android
                    unencryptedBlock = unencryptedBlock.replaceAll("\\\"lastUpdated\\\":\\\"[\\w\\s:,]+\\\",", "");
                    sblock = gson.fromJson(unencryptedBlock, SecureUserDataBlock.class);
                }
                log.log(Level.FINE, "Successfully opened patient document.");
                if (sblock.getPrivateCredentialList() == null || sblock.getPrivateRxList() == null ||
                        sblock.getPrivateRxList().getCurrentList() == null || sblock.getPrivateRxList().getInactiveList() == null) {
                    initializePatientPrivateData();
                    sblock = getPrivatePatientData();
                }
                setRxList(checkRxListConsistency(getUserData(), sblock.getPrivateRxList()));
                log.log(Level.FINE, "Current RxList Count: " + this.getRxList().getCurrentList().size());
                getUserData().restorePrivateData(sblock);
                log.log(Level.FINE, "Account Count: " + this.getUserData().getCredential().size());
                if (sblock.getAllergicSubstancesUNIIs() != null)
                    getAllergicSubstancesUNIIs().addAll(sblock.getAllergicSubstancesUNIIs());
                if (sblock.getPresentConditionsNUIs() != null)
                    getPresentConditionsNUIs().addAll(sblock.getPresentConditionsNUIs());
                if (sblock.getDismissedAlerts() != null)
                    getDismissedAlerts().addAll(sblock.getDismissedAlerts());
                if (sblock.getProviderIdList() != null)
                    setProviderIdList(sblock.getProviderIdList());
                if (sblock.getPharmacyIdList() != null)
                    setPharmacyIdList(sblock.getPharmacyIdList());
            } else {
                log.log(Level.FINE, "No secure data block text set.");
            }
        } catch (InvalidHMACException e) {
            throw new PFMasterPasswordException("Invalid HMAC", (getSecureUserDataBlockText() != null));
        } catch (CryptorException e) {
            log.log(Level.SEVERE, "Error decrypting body.", e);
            throw new PFMasterPasswordException("Password is incorrect.", true);
        }
    }

    public void closePatientDocument(char[] password) throws EncryptionException {
        if (password == null) throw new EncryptionException("Master password is not set.");
        SecureUserDataBlock encBlock = getPrivatePatientData();
        if (encBlock == null) {
            initializePatientPrivateData();
            encBlock = getPrivatePatientData();
        }

        encBlock.setPrivateRxList(getRxList());
        encBlock.setAllergicSubstancesUNIIs(getAllergicSubstancesUNIIs());
        encBlock.setPresentConditionsNUIs(getPresentConditionsNUIs());
        encBlock.setDismissedAlerts(getDismissedAlerts());
        encBlock.setPharmacyIdList(getPharmacyIdList());
        encBlock.setProviderIdList(getProviderIdList());
        userData.saveAndClearPrivateData(encBlock);
        try {
            byte[] encBytes = cryptor.encryptData(gson.toJson(getPrivatePatientData()).getBytes(), password);
            setSecureUserDataBlock(BaseEncoding.base64().encode(encBytes));

            privatePatientData = null;
            masterPrescriptionsList = null;
            allergicSubstancesUNIIs = null;
            presentConditionsNUIs = null;
            setPharmacyIdList(null);
            setProviderIdList(null);
            userData.setCredential(null);
            rxList = null;
            dismissedAlerts = null;
            log.log(Level.FINE, "Patient document closed.");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not encrypt patient data.", e);
            throw new EncryptionException("Cannot encrypt patient document.", e);
        }
    }

    private void initializePatientPrivateData() {
        if (getPrivatePatientData() == null) {
            setPrivatePatientData(new SecureUserDataBlock());
        }
        if (getPrivatePatientData().getPrivateRxList() == null) {
            getPrivatePatientData().setPrivateRxList(new RxListType());
            getPrivatePatientData().getPrivateRxList()
                    .setCurrentList(new ArrayList<String>());
            getPrivatePatientData().getPrivateRxList()
                    .setHiddenList(new ArrayList<String>());
            getPrivatePatientData().getPrivateRxList()
                    .setInactiveList(new ArrayList<String>());
            getPrivatePatientData().getPrivateRxList()
                    .setOtcList(new ArrayList<String>());
            getPrivatePatientData().setDismissedAlerts(new HashSet<String>());
            getPrivatePatientData().setPrivateCredentialList(new ArrayList<Credential>());
        } else {
            getPrivatePatientData().getPrivateRxList().getCurrentList()
                    .clear();
            getPrivatePatientData().getPrivateRxList().getHiddenList()
                    .clear();
            getPrivatePatientData().getPrivateRxList()
                    .getInactiveList().clear();
        }
    }
}
