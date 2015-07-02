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

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DrugInformationType implements Serializable {

    private static final long serialVersionUID = -1L;

    private String medicationName;

    private Date lastUpdated;

    private String ndc;

    private List<String> proprietaryName;

    private String proprietaryNameSuffix;

    private List<String> nonProprietaryName;

    private String dosageFormName;

    private String routeName;

    private String productType;

    private String marketingCategoryName;

    private String labelerName;

    private List<String> substanceName;

    private String activeNumeratorStrength;

    private String activeIngredientUnit;

    private List<String> pharmClasses;

    private String deaSchedule;

    private String drugUseSummary;

    private String brandNames;

    private String rxNormId;

    private String rxNormNui;

    private List<DrugPackage> drugPackages;

    private List<String> upcs;

    private String purpose;

    private String additionalInfoUrl;

    private String pillImageUrl;

    private String drugShape;

    private String drugStrength;

    private String drugColor;

    private List<String> drugColors;

    private String drugImprint;


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
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
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
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getProprietaryName() {
        return this.proprietaryName;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setProprietaryName(List<String> proprietaryName) {
        this.proprietaryName = proprietaryName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getProprietaryNameSuffix() {
        return this.proprietaryNameSuffix;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setProprietaryNameSuffix(String proprietaryNameSuffix) {
        this.proprietaryNameSuffix = proprietaryNameSuffix;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getNonProprietaryName() {
        return this.nonProprietaryName;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setNonProprietaryName(List<String> nonProprietaryName) {
        this.nonProprietaryName = nonProprietaryName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDosageFormName() {
        return this.dosageFormName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDosageFormName(String dosageFormName) {
        this.dosageFormName = dosageFormName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRouteName() {
        return this.routeName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getProductType() {
        return this.productType;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getMarketingCategoryName() {
        return this.marketingCategoryName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setMarketingCategoryName(String marketingCategoryName) {
        this.marketingCategoryName = marketingCategoryName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getLabelerName() {
        return this.labelerName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setLabelerName(String labelerName) {
        this.labelerName = labelerName;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getSubstanceName() {
        return this.substanceName;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setSubstanceName(List<String> substanceName) {
        this.substanceName = substanceName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getActiveNumeratorStrength() {
        return this.activeNumeratorStrength;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setActiveNumeratorStrength(String activeNumeratorStrength) {
        this.activeNumeratorStrength = activeNumeratorStrength;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getActiveIngredientUnit() {
        return this.activeIngredientUnit;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setActiveIngredientUnit(String activeIngredientUnit) {
        this.activeIngredientUnit = activeIngredientUnit;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getPharmClasses() {
        return this.pharmClasses;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setPharmClasses(List<String> pharmClasses) {
        this.pharmClasses = pharmClasses;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDeaSchedule() {
        return this.deaSchedule;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDeaSchedule(String deaSchedule) {
        this.deaSchedule = deaSchedule;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugUseSummary() {
        return this.drugUseSummary;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugUseSummary(String drugUseSummary) {
        this.drugUseSummary = drugUseSummary;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getBrandNames() {
        return this.brandNames;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setBrandNames(String brandNames) {
        this.brandNames = brandNames;
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
    public String getRxNormNui() {
        return this.rxNormNui;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRxNormNui(String rxNormNui) {
        this.rxNormNui = rxNormNui;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.DrugPackage>
     */
    public List<DrugPackage> getDrugPackages() {
        return this.drugPackages;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.DrugPackage>
     */
    public void setDrugPackages(List<DrugPackage> drugPackages) {
        this.drugPackages = drugPackages;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getUpcs() {
        return this.upcs;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setUpcs(List<String> upcs) {
        this.upcs = upcs;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPurpose() {
        return this.purpose;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getAdditionalInfoUrl() {
        return this.additionalInfoUrl;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setAdditionalInfoUrl(String additionalInfoUrl) {
        this.additionalInfoUrl = additionalInfoUrl;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPillImageUrl() {
        return this.pillImageUrl;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPillImageUrl(String pillImageUrl) {
        this.pillImageUrl = pillImageUrl;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugShape() {
        return this.drugShape;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugShape(String drugShape) {
        this.drugShape = drugShape;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugStrength() {
        return this.drugStrength;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugStrength(String drugStrength) {
        this.drugStrength = drugStrength;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugColor() {
        return this.drugColor;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugColor(String drugColor) {
        this.drugColor = drugColor;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getDrugColors() {
        return this.drugColors;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setDrugColors(List<String> drugColors) {
        this.drugColors = drugColors;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getDrugImprint() {
        return this.drugImprint;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setDrugImprint(String drugImprint) {
        this.drugImprint = drugImprint;
    }

}
