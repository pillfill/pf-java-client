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
package com.apothesource.pillfill.service.patient;

import com.apothesource.pillfill.datamodel.PFAuthTokenResponse;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.network.PFNetworkManager;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;

import com.apothesource.pillfill.service.PFServiceEndpoints;
import com.google.common.io.BaseEncoding;

import org.cryptonode.jncryptor.AES256JNCryptor;
import org.cryptonode.jncryptor.CryptorException;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import rx.Observable;

import timber.log.Timber;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */


public class PatientLoginService {

    public Observable<PFAuthTokenResponse> codeLogin(String token) {
        final String url = String.format(PFServiceEndpoints.CODE_URL, token);
        Timber.d("CodeLogin", "Accessing url: %s", url);
        return getAuthTokenResponse(url);
    }

    private PFAuthTokenResponse parseAccessTokenResponse(String token) {
        PFAuthTokenResponse authTokenResponse = PFAuthTokenResponse.parseAuthTokenResponse(token);
        AuthToken tk = AuthToken.parse(token);
        assert tk != null;
        if (authTokenResponse.authToken == null && tk.getEmail() != null) {//Then we've received an authtoken (instead of an authtokenresponse), so create the response
            authTokenResponse = new PFAuthTokenResponse();
            authTokenResponse.success = true;
            authTokenResponse.errorCode = 200;
            authTokenResponse.authToken = tk;
        }
        return authTokenResponse;
    }

    //Create an anonymous account
    public Observable<PFAuthTokenResponse> anonymousTokenLogin(final LoginStatusListener loginListener) {
        String url = PFServiceEndpoints.ANONYMOUS_TOKEN_URL;
        return getAuthTokenResponse(url);
    }

    public Observable<PFAuthTokenResponse> pwdLogin(String username, String pwd) {
        try {
            String hash = getPwdHash(username, pwd);
            String url = String.format(PFServiceEndpoints.PWD_LOGIN_URL, username, hash);
            return getAuthTokenResponse(url);
        } catch (CryptorException ex) {
            Logger.getLogger(PatientLoginService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    private Observable<PFAuthTokenResponse> getAuthTokenResponse(String url){
        return subscribeIoObserveImmediate(Observable.create(subscriber -> {
            try {
                Timber.d("Getting AuthToken for with URL: %s ", url);
                String pfToken = PFNetworkManager.doPinnedGetForUrl(url);
                Timber.d("AuthToken response received. Token: %s", pfToken);
                subscriber.onNext(parseAccessTokenResponse(pfToken));
            } catch (Exception e) {
                Timber.e(e, "Login failed with exception: %s", e.getMessage());
                PFAuthTokenResponse response = new PFAuthTokenResponse();
                response.exception = e;
                subscriber.onNext(response);
            }
            subscriber.onCompleted();
        }));
    }

    public static String getPwdHash(String username, char[] password) throws CryptorException {
        return getPwdHash(username, new String(password));
    }

    public static String getPwdHash(String username, String password) throws CryptorException {
        AES256JNCryptor jn = new AES256JNCryptor();
        char[] concat = (username + password).toCharArray();
        byte[] salt = new byte[8];
        Arrays.fill(salt, (byte) 0);

        SecretKey newKey = jn.keyForPassword(concat, salt); //PBKDF2 10K Iterations
        return BaseEncoding.base64Url().omitPadding().encode(newKey.getEncoded());
    }

    public interface LoginStatusListener {
        void onLoginComplete(PFAuthTokenResponse authToken);
    }

}
