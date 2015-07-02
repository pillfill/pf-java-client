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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Date;

public class AuthToken implements Serializable {
    private static final Gson gson = new GsonBuilder().setDateFormat("MMddyyyyhhmmssZ").disableHtmlEscaping().create();
    String email;
    String agent;
    Date expires;
    Date issued;
    String sig;
    String rawToken;


    public AuthToken(String email, Date issued, Date expires) {
        this(email, email, issued, expires);
    }

    public AuthToken(String email, String agent, Date issued, Date expires) {
        this.email = email;
        this.agent = agent;
        this.expires = expires;
        this.issued = issued;
    }

    public static AuthToken parse(String pfToken) {
        AuthToken at = gson.fromJson(pfToken, AuthToken.class);
        if (at == null) return null;
        if (at.getRawToken() == null) at.setRawToken(pfToken);
        return at;
    }

    public static String getJsonString(AuthToken token) {
        return gson.toJson(token);
    }

    public String toAuthTokenHeaderString() {
        return rawToken;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return the expires
     */
    public Date getExpires() {
        return expires;
    }

    /**
     * @return the issued
     */
    public Date getIssued() {
        return issued;
    }

    /**
     * @return the signature
     */
    public String getSignature() {
        return sig;
    }

    /**
     * @return the signature
     */
    public void setSignature(String signature) {
        this.sig = signature;
    }

    /**
     * @return the agent
     */
    public String getAgent() {
        return agent;
    }

    public String getRawToken() {
        return rawToken;
    }

    public void setRawToken(String rawToken) {
        this.rawToken = rawToken;
    }
}
