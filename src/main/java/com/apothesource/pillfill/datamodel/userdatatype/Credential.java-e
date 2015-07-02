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
package com.apothesource.pillfill.datamodel.userdatatype;

import com.apothesource.pillfill.datamodel.UserKeyValueDataType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Credential implements Serializable {

    private static final long serialVersionUID = -1L;

    private String username;
    private String password;
    private Boolean deleted;
    private List<UserKeyValueDataType> accountData = new ArrayList<UserKeyValueDataType>();
    private boolean valid;
    private boolean newAccount;
    private Date lastUpdated;
    private String source;
    private String name;
    private String id;
    private String dob;
    private int totalRxCount;
    private boolean requiresSecondaryAuth;
    private String errorMessage;
    transient private boolean isReloading = false;

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof Credential)) {
            return false;
        }
        return this.name.toLowerCase().equals(((Credential) other).name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * public getter
     *
     * @returns java.lang.Boolean
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * public setter
     *
     * @param java.lang.Boolean
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.UserKeyValueDataType>
     */
    public List<UserKeyValueDataType> getAccountData() {
        return this.accountData;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.UserKeyValueDataType>
     */
    public void setAccountData(List<UserKeyValueDataType> accountData) {
        this.accountData = accountData;
    }

    /**
     * public getter
     *
     * @returns boolean
     */
    public boolean isValid() {
        return this.valid;
    }

    /**
     * public setter
     *
     * @param boolean
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * public getter
     *
     * @returns boolean
     */
    public boolean getNewAccount() {
        return this.newAccount;
    }

    /**
     * public setter
     *
     * @param boolean
     */
    public void setNewAccount(boolean newAccount) {
        this.newAccount = newAccount;
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
     * @returns java.lang.String
     */
    public String getName() {
        return this.name;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isReloading() {
        return isReloading;
    }

    public void setReloading(boolean isReloading) {
        this.isReloading = isReloading;
    }

    public int getTotalRxCount() {
        return totalRxCount;
    }

    public void setTotalRxCount(int totalRxCount) {
        this.totalRxCount = totalRxCount;
    }

    public boolean isRequiresSecondaryAuth() {
        return requiresSecondaryAuth;
    }

    public void setRequiresSecondaryAuth(boolean requiresSecondaryAuth) {
        this.requiresSecondaryAuth = requiresSecondaryAuth;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
