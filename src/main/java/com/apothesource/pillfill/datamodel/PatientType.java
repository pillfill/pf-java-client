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
package com.apothesource.pillfill.datamodel;

import com.apothesource.pillfill.datamodel.android.SecureUserDataBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class PatientType implements Serializable {

    private static final long serialVersionUID = -1L;

    protected PfAccountType pfAccount = new PfAccountType();

    protected UserDataType userData = new UserDataType();

    protected PrescriptionListType masterPrescriptionsList = new PrescriptionListType();

    protected RxListType rxList;

    protected String secureUserDataBlock;

    protected Date serTime;
    protected SecureUserDataBlock privatePatientData = new SecureUserDataBlock();
    //Local data- Encrypted as part of the profile, but not visible on the backend
    protected HashSet<String> allergicSubstancesUNIIs = new HashSet<String>();
    protected HashSet<String> presentConditionsNUIs = new HashSet<String>();
    protected HashSet<String> dismissedAlerts = new HashSet<String>(); //This also includes dismissed prescribers and pharmacies
    protected String _id, _rev;
    private ArrayList<String> providerIdList = new ArrayList<String>();
    private ArrayList<String> pharmacyIdList = new ArrayList<String>();
    private transient PFAuthTokenResponse loginResponse;


    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.PfAccountType
     */
    public PfAccountType getPfAccount() {
        return this.pfAccount;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.PfAccountType
     */
    public void setPfAccount(PfAccountType pfAccount) {
        this.pfAccount = pfAccount;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.UserDataType
     */
    public UserDataType getUserData() {
        return this.userData;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.UserDataType
     */
    public void setUserData(UserDataType userData) {
        this.userData = userData;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.PrescriptionListType
     */
    public PrescriptionListType getMasterPrescriptionsList() {
        return this.masterPrescriptionsList;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.PrescriptionListType
     */
    public void setMasterPrescriptionsList(PrescriptionListType masterPrescriptionsList) {
        if (masterPrescriptionsList == null) {
            throw new IllegalArgumentException("Master prescription list cannot be null.");
        }
        this.masterPrescriptionsList = masterPrescriptionsList;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.RxListType
     */
    public RxListType getRxList() {
        return this.rxList;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.RxListType
     */
    public void setRxList(RxListType rxList) {
        this.rxList = rxList;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSecureUserDataBlockText() {
        return secureUserDataBlock;
    }

    public void setSecureUserDataBlock(String secureUserDataBlock) {
        this.secureUserDataBlock = secureUserDataBlock;
    }

    public SecureUserDataBlock getPrivatePatientData() {
        return privatePatientData;
    }

    public void setPrivatePatientData(SecureUserDataBlock privatePatientData) {
        this.privatePatientData = privatePatientData;
    }

    public HashSet<String> getAllergicSubstancesUNIIs() {
        return allergicSubstancesUNIIs;
    }

    public void setAllergicSubstancesUNIIs(HashSet<String> allergicSubstancesUNIIs) {
        this.allergicSubstancesUNIIs = allergicSubstancesUNIIs;
    }

    public HashSet<String> getPresentConditionsNUIs() {
        return presentConditionsNUIs;
    }

    public void setPresentConditionsNUIs(HashSet<String> presentConditionsNUIs) {
        this.presentConditionsNUIs = presentConditionsNUIs;
    }

    public HashSet<String> getDismissedAlerts() {
        return dismissedAlerts;
    }

    public void setDismissedAlerts(HashSet<String> dismissedAlerts) {
        this.dismissedAlerts = dismissedAlerts;
    }

    public Date getSerTime() {
        return serTime;
    }

    public void setSerTime(Date serTime) {
        this.serTime = serTime;
    }

    public ArrayList<String> getProviderIdList() {
        return providerIdList;
    }

    public void setProviderIdList(ArrayList<String> providerIdList) {
        this.providerIdList = providerIdList;
    }

    public ArrayList<String> getPharmacyIdList() {
        return pharmacyIdList;
    }

    public void setPharmacyIdList(ArrayList<String> pharmacyIdList) {
        this.pharmacyIdList = pharmacyIdList;
    }

    public PFAuthTokenResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(PFAuthTokenResponse loginResponse) {
        this.loginResponse = loginResponse;
    }
}
