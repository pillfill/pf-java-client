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

import com.apothesource.pillfill.datamodel.RxListType;
import com.apothesource.pillfill.datamodel.ndfrt.Concept;
import com.apothesource.pillfill.datamodel.userdatatype.Credential;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.cryptonode.jncryptor.JNCryptor;


/**
 * This is a DTO for the parameters that should not be accessible to the
 * PillFill server. It protects the most sensitive user parameters, including
 * the their list of prescriptions IDs, by separating it from the rest of 
 * the PatientType information. It is encrypted using {@link JNCryptor}, Base64
 * encoded, and then inserted into the PatientType.
 * 
 * {@link SecurePatientTypeWrapper} is responsible for encryption/decrypting
 * this object. On decryption, it will copy the sensitive fields from here into
 * a working copy of the PatientType which can then be edited. Before syncing to
 * the server, it will copy sensitive fields from the working copy of the 
 * PatientType into this object, null its references, and then encrypt.
 * 
 * @author Apothesource, Inc
 */
public class SecureUserDataBlock {
    private static final int DEFAULT_HEIGHT_IN_CMS = 178;
    private static final int DEFAULT_WEIGHT_IN_KGS = 80;
    
    private int heightInCm = DEFAULT_HEIGHT_IN_CMS;
    private float weightInKgs = DEFAULT_WEIGHT_IN_KGS;
    private Date dob = new Date();
    private RxListType privateRxList;
    private List<Credential> privateCredentialList;
    private List<Concept> activeIngredients = new ArrayList<>();
    private HashSet<String> allergicSubstancesUNIIs = new HashSet<>();
    private HashSet<String> dismissedAlerts = new HashSet<>();
    private HashSet<String> presentConditionsNUIs = new HashSet<>();
    private ArrayList<String> providerIdList = new ArrayList<>();
    private ArrayList<String> pharmacyIdList = new ArrayList<>();

    public RxListType getPrivateRxList() {
        return privateRxList;
    }

    public void setPrivateRxList(RxListType privateRxList) {
        this.privateRxList = privateRxList;
    }

    public List<Credential> getPrivateCredentialList() {
        return privateCredentialList;
    }

    public void setPrivateCredentialList(List<Credential> privateCredentialList) {
        this.privateCredentialList = privateCredentialList;
    }

    public HashSet<String> getPresentConditionsNUIs() {
        return presentConditionsNUIs;
    }

    public void setPresentConditionsNUIs(HashSet<String> presentConditionsNUIs) {
        this.presentConditionsNUIs = presentConditionsNUIs;
    }

    public List<Concept> getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(List<Concept> activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    public HashSet<String> getAllergicSubstancesUNIIs() {
        return allergicSubstancesUNIIs;
    }

    public void setAllergicSubstancesUNIIs(HashSet<String> allergicSubstancesUNIIs) {
        this.allergicSubstancesUNIIs = allergicSubstancesUNIIs;
    }

    public HashSet<String> getDismissedAlerts() {
        return dismissedAlerts;
    }

    public void setDismissedAlerts(HashSet<String> dismissedAlerts) {
        this.dismissedAlerts = dismissedAlerts;
    }

    public Float getWeightInKgs() {
        return weightInKgs;
    }

    public void setWeightInKgs(Float weightInKgs) {
        this.weightInKgs = weightInKgs;
    }

    public Integer getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Integer heightInCm) {
        this.heightInCm = heightInCm;
    }

    public Date getDoB() {
        return dob;
    }

    public void setDoB(Date dob) {
        this.dob = dob;
    }

    public ArrayList<String> getProviderIdList() {
        return providerIdList;
    }

    public void setProviderIdList(ArrayList<String> providerList) {
        this.providerIdList = providerList;
    }

    public ArrayList<String> getPharmacyIdList() {
        return pharmacyIdList;
    }

    public void setPharmacyIdList(ArrayList<String> pharmacyList) {
        this.pharmacyIdList = pharmacyList;
    }

}
