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
package com.apothesource.pillfill.network;

import com.apothesource.pillfill.service.patient.PatientServiceParams;
import com.google.common.io.BaseEncoding;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.X509TrustManager;

/**
 * A custom TrustManager that ensures that the public key of a TLS connection
 * matches a specified public key.
 * 
 * @author Apothesource, Inc
 */
public class PillFillTrustManager implements X509TrustManager {
    private boolean allowWeakCiphers = false;
    
    public PillFillTrustManager(){
        this(false);
    }
    
    public PillFillTrustManager(boolean allowWeakCiphers){
        this.allowWeakCiphers = allowWeakCiphers;
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        if (chain == null) {
            throw new IllegalArgumentException(
                    "checkServerTrusted: X509Certificate array is null");
        }

        if (!(chain.length > 0)) {
            throw new IllegalArgumentException(
                    "checkServerTrusted: X509Certificate is empty");
        }

        if (!(null != authType && authType.equalsIgnoreCase("ECDHE_RSA"))) {
            if(!allowWeakCiphers){
                throw new CertificateException(
                        "checkServerTrusted: AuthType is not ECDHE_RSA- is: " + authType);
            }
        }

        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
        String encoded = BaseEncoding.base64().encode(pubkey.getEncoded());

        for (String trustedKey : PatientServiceParams.CERT_PUBLIC_KEY) {
            if (trustedKey.equals(encoded)) return;
        }
        throw new CertificateException(
                "checkServerTrusted Failed: Got public key:" + encoded);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        // TODO Auto-generated method stub

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }

}