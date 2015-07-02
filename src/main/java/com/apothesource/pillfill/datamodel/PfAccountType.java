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
import java.util.ArrayList;
import java.util.List;

public class PfAccountType implements Serializable {

    public static final String COM_PILLFILL_USER_VERIFIED = "com.pillfill.user.verified";


    private static final long serialVersionUID = -1L;


    private String accountPassword;

    private String accountKey;

    private String pfPassword;

    private String pfUsername;

    private Boolean debugAccount = false;

    private List<String> pfEntitlements = new ArrayList<String>();

    private List<OAuthTokenType> pairedAuthTokens = new ArrayList<OAuthTokenType>();

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getAccountPassword() {
        return this.accountPassword;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getAccountKey() {
        return this.accountKey;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public boolean isPendingVerification() {
        return false;
    }

    public String getPfPassword() {
        return pfPassword;
    }

    public void setPfPassword(String pfPassword) {
        this.pfPassword = pfPassword;
    }

    public String getPfUsername() {
        return pfUsername;
    }

    public void setPfUsername(String pfUsername) {
        this.pfUsername = pfUsername;
    }

    public List<OAuthTokenType> getPairedAuthTokens() {
        return pairedAuthTokens;
    }

    public void setPairedAuthTokens(List<OAuthTokenType> pairedAuthTokens) {
        this.pairedAuthTokens = pairedAuthTokens;
    }

    public List<String> getPfEntitlements() {
        return pfEntitlements;
    }

    public void setPfEntitlements(List<String> pfEntitlements) {
        this.pfEntitlements = pfEntitlements;
    }

    public Boolean isDebugAccount() {
        return debugAccount;
    }

    public void setDebugAccount(Boolean debugAccount) {
        this.debugAccount = debugAccount;
    }
}
