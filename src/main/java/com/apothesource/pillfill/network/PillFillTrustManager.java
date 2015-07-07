/*
 *
 *  * The MIT License
 *  *
 *  * Copyright {$YEAR} Apothesource, Inc.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.apothesource.pillfill.network;

import com.google.common.io.BaseEncoding;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.X509TrustManager;

/**
 * A custom TrustManager that ensures that the public key of a TLS connection
 * matches a trusted specified public key.
 * 
 * @author Apothesource, Inc
 */
public class PillFillTrustManager implements X509TrustManager {
    public static final String AUTH_ECDHE_RSA = "ECDHE_RSA";
    private boolean allowWeakCiphers = false;
    /**
     * List of Default PillFill Trusted Public Keys in BASE64
     * Corresponds to keys used on developer.pillfill.com and rxservices.pillfill.com
     */
    public static final String PF_CERT_PUBLIC_KEYS[] = new String[]{
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnwIeJvIAaMOAAP1foR438M5Q8rTGxdaY1SgbsFmmoxnTCt5BxNErAZFrb4e6XINTqPtxr06V+d8SSZ5ztA7lO//Bix8f6NXPP+EMxrZL/AJj5LGqeqh5vgbIaJNHYWAgAW8TLsdZ8tju8EyU6MbIah4sJpFSVhlw1KzM08NPXXdskkfNNfBG5VOxXjf3cGDD3rFHjyzoPhN43ZK+TOL2CP1Ug5YdCzzQzOVPODwlWPeV7omnRCiRq6N4Y1PxoxyvHyr/TeIDCgDY8a1hvEBb+MrCa4yDnYyTw3kJNfI2pyRwkydBUMTBRG4cwpqTupOOCbdWYyrSrTzJ4G1PB6L3twIDAQAB",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv0Ktz4F0ao/9F3faM09sAxPb9lqQFncWxN16rEzqTtaiQYpmzSeUgmt964YjBViwHALksLgrrN/skluo6Wm7goD9E/QntrQMVC+AcsQyMMpwFb3bxWgiR7Oc7xDqaIlczypFmbSKIFehitxIyxI6omrmHv2OX3DOKtwLHN+yI2itObMPXC9sE8ozkaa9uj+zGNnHKx2LPuB+ZbuIOXHgFl5Rhqp/qrTiLB7cSI91kVxe+5msO2MrcsgiGG1kCdXbZ79LCokqjqzX9otmDgij7HtF0tb2ufYEBziuJ9ip1oY30UcLkooUkxA/KwEASiR7v+qkd/fU3vXUj1SqRixrbQIDAQAB",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA07bDcilKU0pQbs/8TR0QasSFp85oJOY4BJImeNrqYkl4xtSe6JTdBpvJE7BdtDfa71d0JeOIW5dcX32Ynq8makl0d7KfMLDx7/A49fnBjK++vIBfQoWjuQsMoWejNW9PvPyzLIvJnFjik8dv1YlpjJNajcK3vXlHJLOpMjaX17Me5XopFULiTrw3cKhYEg38REyNrOp1/gLGOrQseXPTXtoK3Ow0Sa6yHubw3VUEhP3BlxZ5CpeNmGTkKR+L1IjngNPEQ0Zhqdo+ZOJURHbjzN39peXfRgQFut/ODg5oDv+RQPOUxDeh7C8UJDu5uBqjJGfdb78Av6ApwiFAPlGY7wIDAQAB",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuLFuGF0GgN4Wl22SK2J5grJgB6EFm9BxpoYn3b5icCWBSysaershwMV27hrSBjl802+vd8pH6npwBKiYUBhXnT2YWt9oVB5BIkZ6QvTZ18Q4ih3Ti7IWtmzNJyDSL/gSnNYgwKrIPrK3NILr3OIb2cZoehbbtGesqYQB2ezyLLOwFMEYzQBPBt29nxjxrQsFaNrPEUsbGwhvan2/0Pykd3hli07jEDHt3INfsN88PXKeQHiDQ5YNjDjuJ7kn9FXTjxVJqUGeYP+ZsC54McDN9AGJfkZ4jLfRhg6rjYe7m6UC1XT9MNZUgKAZQTKfK5zea4YWMbFizUzQ31gTq3IngwIDAQAB"
    };

    public final String[] trustedKeys;

    public PillFillTrustManager(){
        this(false, PF_CERT_PUBLIC_KEYS);
    }
    public PillFillTrustManager(boolean allowWeakCiphers){
        this(allowWeakCiphers, PF_CERT_PUBLIC_KEYS);
    }
    public PillFillTrustManager(boolean allowWeakCiphers, String[] publicKeys){
        this.allowWeakCiphers = allowWeakCiphers;
        this.trustedKeys = publicKeys;
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

        if (!(null != authType && authType.equalsIgnoreCase(AUTH_ECDHE_RSA))) {
            if(!allowWeakCiphers){
                throw new CertificateException(
                        "checkServerTrusted: AuthType is not ECDHE_RSA- is: " + authType);
            }
        }

        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
        String encoded = BaseEncoding.base64().encode(pubkey.getEncoded());

        for (String trustedKey : PF_CERT_PUBLIC_KEYS) {
            if (trustedKey.equals(encoded)) return;
        }
        throw new CertificateException(
                "checkServerTrusted Failed: Got public key:" + encoded);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
        //No client validation

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        //Don't filter on issuer
        return null;
    }

}
