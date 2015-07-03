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

import com.apothesource.pillfill.datamodel.PFCheckAvailableAccountResponse;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.PFServiceEndpoints;
import com.apothesource.pillfill.utilites.ReactiveUtils;

import java.io.IOException;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Michael Ramirez on 6/12/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class PatientUsernameQuery {
    private final AuthToken token;


    public PatientUsernameQuery(AuthToken authToken) {
        token = authToken;
    }

    public Observable<PFCheckAvailableAccountResponse> checkIsUsernameAvailable(String username){
        final String url = String.format(PFServiceEndpoints.CHECK_USERNAME_URL, token.getEmail(), username);

        return ReactiveUtils.subscribeIoObserveImmediate(subscriber -> {
            try{
                Timber.i("Connecting to URL: %s,", url);
                String response = PFNetworkManager.doPinnedGetForUrl(url, token.toAuthTokenHeaderString());
                subscriber.onNext(PFCheckAvailableAccountResponse.parseResponse(response));
                subscriber.onCompleted();
            }catch(IOException e){
                Timber.e(e, "Couldn't connect to account service to check for available username: %s", e.getMessage());
                subscriber.onError(e);
            }
        });
    }

}

