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

import com.apothesource.pillfill.utilites.DateAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PrescriptionType implements Serializable {

    //A set of attributes that aren't necessarily part of the prescription data 'proper', but are frequently needed
    public static final String METADATA_KEY_IS_OTC = "isOtc";
    public static final String METADATA_KEY_IS_ACTIVE = "isActive";
    public static final String METADATA_KEY_HAS_REMINDER = "hasReminder";
    public static final String METADATA_KEY_PILL_COLOR = "pillColor";
    public static final String METADATA_KEY_PILL_SHAPE = "pillShape";
    public static final String METADATA_BRAND_NAME = "brandName";
    public static final String METADATA_DRAWABLE_DRUG_FORM_ID = "drawableDfId";


    private static final long serialVersionUID = -1L;
    public HashMap<String, Object> metadata = new HashMap<>();
    private String rxNumber;
    private String status;
    private String medicationName;
    private List<String> brandNames;
    private Date dateWritten;
    private Date dateExpires;
    private Date computedInactiveAfterDate;
    private String pharmacyStoreId;
    private String pharmacist;
    private String pharmacyAddress;
    private Integer daysSupply;
    private Float quantityPerDose, dosesPerDay;
    private Float quantityRemaining;
    private Date dispenseDate;
    private List<String> previousDispenseDates = new ArrayList<String>();
    private List<String> linkedConditions = new ArrayList<String>();
    private String insuranceStatus;
    private String pharmacyPhone;
    private String prescriber;
    private String prescriberNPI;
    private String prescriberAddress;
    private String prescriberPhone;
    private String quantity;
    private Integer refillsAuthorized;
    private String refillsRemaining;
    private String sig;
    private String cost;
    private String insuranceCost;
    private String ndc;
    private String rxNormId;
    private String ndfrtNui;
    private String ndfrtName;
    private String drugImageUrl;
    private String splId;
    private String dosage;
    private String doseType;
    private String fillings;
    private String source;
    private Date collectionDate;
    private Date serTime;
    private DrugInformationType drugInformation;
    private PrescriptionType insuranceReference;
    private Set<DrugAlertType> drugAlerts = new HashSet<>();
    private String uuid;
    private String _rev, _id;

    private boolean isOtc = false;
    private boolean isRxActive = false;

    public static boolean hasReminder(PrescriptionType rx) {
        Boolean hasReminder = (Boolean) rx.metadata.get(METADATA_KEY_HAS_REMINDER);
        return (hasReminder != null) ? hasReminder : false;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRxNumber() {
        return this.rxNumber;
    }

    /**
     * public setter
     *
     * @param String
     */
    public void setRxNumber(String rxNumber) {
        this.rxNumber = rxNumber;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getMedicationName() {
        return this.medicationName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getDateWritten() {
        return this.dateWritten;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setDateWritten(Date dateWritten) {
        this.dateWritten = dateWritten;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getDateExpires() {
        return this.dateExpires;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPharmacyStoreId() {
        return this.pharmacyStoreId;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPharmacyStoreId(String pharmacyStoreId) {
        this.pharmacyStoreId = pharmacyStoreId;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPharmacist() {
        return this.pharmacist;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPharmacist(String pharmacist) {
        this.pharmacist = pharmacist;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPharmacyAddress() {
        return this.pharmacyAddress;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    /**
     * public getter
     *
     * @returns java.lang.Integer
     */
    public Integer getDaysSupply() {
        return this.daysSupply;
    }

    /**
     * public setter
     *
     * @param java.lang.Integer
     */
    public void setDaysSupply(Integer daysSupply) {
        this.daysSupply = daysSupply;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getDispenseDate() {
        return this.dispenseDate;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setDispenseDate(Date dispenseDate) {
        this.dispenseDate = dispenseDate;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getPreviousDispenseDates() {
        return this.previousDispenseDates;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setPreviousDispenseDates(List<String> previousDispenseDates) {
        this.previousDispenseDates = previousDispenseDates;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getInsuranceStatus() {
        return this.insuranceStatus;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setInsuranceStatus(String insuranceStatus) {
        this.insuranceStatus = insuranceStatus;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPharmacyPhone() {
        return this.pharmacyPhone;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPharmacyPhone(String pharmacyPhone) {
        this.pharmacyPhone = pharmacyPhone;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPrescriber() {
        return this.prescriber;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPrescriber(String prescriber) {
        this.prescriber = prescriber;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPrescriberNPI() {
        return this.prescriberNPI;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPrescriberNPI(String prescriberNPI) {
        this.prescriberNPI = prescriberNPI;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPrescriberAddress() {
        return this.prescriberAddress;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPrescriberAddress(String prescriberAddress) {
        this.prescriberAddress = prescriberAddress;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPrescriberPhone() {
        return this.prescriberPhone;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPrescriberPhone(String prescriberPhone) {
        this.prescriberPhone = prescriberPhone;
    }

    /**
     * public getter
     *
     * @returns java.lang.Float
     */
    public String getQuantity() {
        return this.quantity;
    }

    /**
     * public setter
     *
     * @param java.lang.Float
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * public getter
     *
     * @returns java.lang.Integer
     */
    public Integer getRefillsAuthorized() {
        return this.refillsAuthorized;
    }

    /**
     * public setter
     *
     * @param java.lang.Integer
     */
    public void setRefillsAuthorized(Integer refillsAuthorized) {
        this.refillsAuthorized = refillsAuthorized;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRefillsRemaining() {
        return this.refillsRemaining;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRefillsRemaining(String refillsRemaining) {
        this.refillsRemaining = refillsRemaining;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSig() {
        return this.sig;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSig(String sig) {
        this.sig = sig;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getCost() {
        return this.cost;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setCost(String cost) {
        this.cost = cost;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getInsuranceCost() {
        return this.insuranceCost;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setInsuranceCost(String insuranceCost) {
        this.insuranceCost = insuranceCost;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getNdc() {
        return this.ndc;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRxNormId() {
        return this.rxNormId;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRxNormId(String rxNormId) {
        this.rxNormId = rxNormId;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getNdfrtNui() {
        return this.ndfrtNui;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setNdfrtNui(String ndfrtNui) {
        this.ndfrtNui = ndfrtNui;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getNdfrtName() {
        return this.ndfrtName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setNdfrtName(String ndfrtName) {
        this.ndfrtName = ndfrtName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugImageUrl() {
        return this.drugImageUrl;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugImageUrl(String drugImageUrl) {
        this.drugImageUrl = drugImageUrl;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSplId() {
        return this.splId;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSplId(String splId) {
        this.splId = splId;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDosage() {
        return this.dosage;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDoseType() {
        return this.doseType;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDoseType(String doseType) {
        this.doseType = doseType;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getFillings() {
        return this.fillings;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setFillings(String fillings) {
        this.fillings = fillings;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSource() {
        return this.source;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getCollectionDate() {
        return this.collectionDate;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.DrugInformationType
     */
    public DrugInformationType getDrugInformation() {
        return this.drugInformation;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.DrugInformationType
     */
    public void setDrugInformation(DrugInformationType drugInformation) {
        this.drugInformation = drugInformation;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.PrescriptionType
     */
    public PrescriptionType getInsuranceReference() {
        return this.insuranceReference;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.PrescriptionType
     */
    public void setInsuranceReference(PrescriptionType insuranceReference) {
        this.insuranceReference = insuranceReference;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.DrugAlertListType
     */
    public Set<DrugAlertType> getDrugAlerts() {
        return this.drugAlerts;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.DrugAlertListType
     */
    public void setDrugAlerts(Set<DrugAlertType> drugAlerts) {
        this.drugAlerts = drugAlerts;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof PrescriptionType)) {
            return false;
        } else {
            PrescriptionType otherRx = (PrescriptionType) other;
            return otherRx.getUuid().equalsIgnoreCase(uuid);
        }
    }

    public int hashCode() {
        return uuid.hashCode();
    }

    public List<String> getLinkedConditions() {
        return linkedConditions;
    }

    public void setLinkedConditions(List<String> linkedConditions) {
        this.linkedConditions = linkedConditions;
    }

    public Date getComputedInactiveAfterDate() {
        if (computedInactiveAfterDate == null && getDispenseDate() != null) {
            computedInactiveAfterDate = DateAdapter.addMonthsToDate(getDispenseDate(), 3);
        }
        return computedInactiveAfterDate;
    }

    public void setComputedInactiveAfterDate(Date computedInactiveAfterDate) {
        this.computedInactiveAfterDate = computedInactiveAfterDate;
    }

    public Float getQuantityPerDose() {
        return quantityPerDose;
    }

    public void setQuantityPerDose(Float quantityPerDose) {
        this.quantityPerDose = quantityPerDose;
    }

    public Float getDosesPerDay() {
        return dosesPerDay;
    }

    public void setDosesPerDay(Float dosesPerDay) {
        this.dosesPerDay = dosesPerDay;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public Date getSerTime() {
        return serTime;
    }

    public void setSerTime(Date serDate) {
        this.serTime = serDate;
    }

    public Float getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(Float quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public boolean isOtc() {
        return isOtc;
    }

    public void setOtc(boolean isOtc) {
        this.isOtc = isOtc;
    }

    public List<String> getBrandNames() {
        if (brandNames == null) brandNames = new ArrayList<>();
        return brandNames;
    }

    public void setBrandNames(List<String> brandNames) {
        this.brandNames = brandNames;
    }

    public boolean isActive() {
        return isRxActive;
    }

    public void setRxActive(boolean isActive) {
        isRxActive = isActive;
    }
}
