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
import com.apothesource.pillfill.datamodel.userdatatype.Credential;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDataType implements Serializable {

    private static final long serialVersionUID = -1L;
    protected Integer heightInCms = 178;
    protected Float weightInKgs = 80f;
    private String email;
    private String mobilePhoneNumber;
    private String fname;
    private String lname;
    private String gender;
    private String photoUrl;
    private Date dob;
    private Integer totalRxCount = 0;
    private List<Credential> credential = new ArrayList<Credential>();
    private List<UserKeyValueDataType> userAttribute = new ArrayList<UserKeyValueDataType>();

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getFname() {
        return this.fname;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getLname() {
        return this.lname;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getDob() {
        return this.dob;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.UserDataType.Credential>
     */
    public List<Credential> getCredential() {
        return this.credential;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.UserDataType.Credential>
     */
    public void setCredential(List<Credential> credential) {
        this.credential = credential;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.UserKeyValueDataType>
     */
    public List<UserKeyValueDataType> getUserAttribute() {
        return this.userAttribute;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.UserKeyValueDataType>
     */
    public void setUserAttribute(List<UserKeyValueDataType> userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getTotalRxCount() {
        return totalRxCount;
    }

    public void setTotalRxCount(Integer totalRxCount) {
        this.totalRxCount = totalRxCount;
    }

    public Float getWeightInKgs() {
        return weightInKgs;
    }

    public void setWeightInKgs(Float weightInKgs) {
        this.weightInKgs = weightInKgs;
    }

    public Integer getHeightInCm() {
        return heightInCms;
    }

    public void setHeightInCm(Integer heightInCms) {
        this.heightInCms = heightInCms;
    }

    public void saveAndClearPrivateData(SecureUserDataBlock block) {
        if (weightInKgs != null) {
            block.setWeightInKgs(weightInKgs);
            weightInKgs = null;
        }
        if (heightInCms != null) {
            block.setHeightInCm(heightInCms);
            heightInCms = null;
        }
        if (dob != null) {
            block.setDoB(dob);
            dob = null;
        }
        if (credential != null) {
            block.setPrivateCredentialList(credential);
            credential = null;
        }
    }

    public void restorePrivateData(SecureUserDataBlock block) {
        weightInKgs = block.getWeightInKgs();
        heightInCms = block.getHeightInCm();
        dob = block.getDoB();
        credential = block.getPrivateCredentialList();
    }
}
