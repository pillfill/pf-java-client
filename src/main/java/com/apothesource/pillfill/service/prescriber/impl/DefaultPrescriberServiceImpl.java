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
package com.apothesource.pillfill.service.prescriber.impl;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All
 * Rights Reserved.
 */
import com.apothesource.pillfill.datamodel.PrescriberRxHistory;
import com.apothesource.pillfill.datamodel.PrescriberType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.PFServiceEndpoints;
import com.apothesource.pillfill.service.prescriber.PrescriberService;
import com.apothesource.pillfill.utilites.ResourceUtil;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;
import com.google.common.base.Joiner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import timber.log.Timber;

public class DefaultPrescriberServiceImpl implements PrescriberService {

    private static final String TAG = "PrescriberInfoService";
    private static final Type PRESCRIBER_TYPE_LIST = new TypeToken<ArrayList<PrescriberType>>() {
    }.getType();
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final String apiKey;

    public DefaultPrescriberServiceImpl() {
        apiKey = ResourceUtil.getInstance().getPFApiKey();
    }

    @Override
    public Observable<PrescriberRxHistory> getPrescriberRxHistory(final String id) {
        String url = String.format(PFServiceEndpoints.PRESCRIBER_RX_HISTORY_URL, id);

        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                PrescriberRxHistory history = gson.fromJson(response, PrescriberRxHistory.class);
                subscriber.onNext(history);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Error getting Rx History for Prescriber NPI %s : %s", id, e.toString());
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<PrescriberType> getPrescribersFromPrescriptions(List<PrescriptionType> rxList, List<String> additionalIds) {
        if (rxList == null || rxList.isEmpty()) {
            Timber.e("Cannot load prescribers- Empty rx list.");
            return Observable.empty();
        }
        ArrayList<String> idList = new ArrayList<>();
        for (PrescriptionType rx : rxList) {
            String npi = rx.getPrescriberNPI();
            if (npi != null && !npi.isEmpty() && !idList.contains(npi)) {
                idList.add(npi);
            }
        }
        idList.addAll(additionalIds);
        if (!idList.isEmpty()) {
            return getPrescribers(idList);
        } else {
            Timber.w("Found 0 references to npi's within the prescriptions.");
            return Observable.empty();
        }
    }

    @Override
    public Observable<PrescriberType> getPrescribersByPhone(final String phone) {
        String phoneFiltered = phone.replaceAll("[\\D]", "").replaceAll("^[1]", "");
        String url = String.format(PFServiceEndpoints.PRESCRIBER_PHONE_SEARCH_URL, phoneFiltered);
        Timber.v("Requesting prescriber info url: %s", url);
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                ArrayList<PrescriberType> drs = gson.fromJson(response, PRESCRIBER_TYPE_LIST);
                Observable.from(drs).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e, "Couldn't get prescriber information.");
                subscriber.onError(e);
            }
        });
    }

    private Observable<PrescriberType> getPrescribers(List<String> drIds) {
        if (drIds == null || drIds.isEmpty()) {
            Timber.e("Cannot load prescribers- Empty npi list.");
            return Observable.empty();
        }
        String idList = Joiner.on("&ids=").join(drIds);
        String url = String.format(PFServiceEndpoints.PRESCRIBER_URL, idList);
        Timber.v("Requesting prescriber info url: %s", url);
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                ArrayList<PrescriberType> drs = gson.fromJson(response, PRESCRIBER_TYPE_LIST);
                Observable.from(drs).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e, "Couldn't get prescriber information.");
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<PrescriberType> searchForPrescribers(String state, String lName, String fName) {
        if (state == null || state.length() != 2 || lName == null || lName.trim().isEmpty()) {
            throw new IllegalArgumentException("2-letter state abbreviation and last name and must be set.");
        }
        fName = (fName == null) ? "" : fName.toUpperCase();
        lName = lName.toUpperCase();
        state = state.toUpperCase();

        String url = String.format(PFServiceEndpoints.PRESCRIBER_STATE_NAME_SEARCH_URL, state, lName, fName);
        Timber.v("Requesting prescriber info url: %s", url);

        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                ArrayList<PrescriberType> drs = gson.fromJson(response, PRESCRIBER_TYPE_LIST);
                Observable.from(drs).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e, "Couldn't get prescriber information.");
                subscriber.onError(e);
            }
        });
    }

}
