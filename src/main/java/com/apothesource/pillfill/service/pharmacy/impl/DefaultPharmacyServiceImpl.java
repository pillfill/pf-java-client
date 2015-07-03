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
package com.apothesource.pillfill.service.pharmacy.impl;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */

import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.android.GooglePlaceSearchResult.PlaceSearchResponse;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.PFServiceEndpoints;
import com.apothesource.pillfill.service.pharmacy.PharmacyService;
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
import java.util.Locale;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DefaultPharmacyServiceImpl implements PharmacyService {

    private static final String TAG = "PharmacyInfoService";
    private static final Type PHARMACY_LIST_TYPE = new TypeToken<ArrayList<PharmacyType>>() {}.getType();
    private Gson gson;
    private final String apiKey;


    public DefaultPharmacyServiceImpl() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        apiKey = ResourceUtil.getInstance().getPFApiKey();
    }

    @Override
    public Observable<PharmacyType> getPharmaciesFromPrescriptions(List<PrescriptionType> rxList, List<String> additionalIds) {
        ArrayList<String> idList = new ArrayList<String>();
        for (PrescriptionType rx : rxList) {
            String pharmacyId = rx.getPharmacyStoreId();
            if (pharmacyId != null && !idList.contains(pharmacyId) && !pharmacyId.equals("UNKNOWN")) {
                idList.add(pharmacyId);
            }
        }
        idList.addAll(additionalIds);
        return getPharmacies(idList);
    }

    @Override
    public Observable<PlaceSearchResponse> searchForPharmacyPlacesNearLocation(String keyword, String pgtoken, double lat, double lng, int radius) {
        keyword = (keyword == null) ? "" : keyword;
        pgtoken = (pgtoken == null) ? "" : pgtoken;
        String url = String.format(Locale.US, PFServiceEndpoints.PHARMACY_SEARCH_URL, keyword, lat, lng, radius, pgtoken);
        Timber.d("Accessing pharmacy lookup URL: %s", url);
        Observable<PlaceSearchResponse> observable = Observable.create(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                subscriber.onNext(gson.fromJson(response, PlaceSearchResponse.class));
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Could not search for pharmacies: %s", e.toString());
                subscriber.onError(e);
            }
        });
        return observable.subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<PharmacyType> getPharmacyPlace(String ref) {
        String url = String.format(Locale.US, PFServiceEndpoints.PHARMACY_GET_PLACE_URL, ref);
        Timber.d("Pulling pharmacy place URL: %s", url);
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                PharmacyType pharmacy = gson.fromJson(response, PharmacyType.class);
                if(pharmacy != null){
                    subscriber.onNext(pharmacy);
                }
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Error retrieving pharmacy information: %s", e.toString());
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<PharmacyType> getPharmacies(List<String> pharmacyIds) {
        if (pharmacyIds == null || pharmacyIds.isEmpty()) {
            Timber.i("No pharmacies. Not loading.");
            return Observable.empty();
        }
        return subscribeIoObserveImmediate(subscriber -> {
            String idList = Joiner.on("&ids=").join(pharmacyIds);
            String url = String.format(PFServiceEndpoints.PHARMACY_URL, idList);
            Timber.d("Requesting pharmacy info url: " + url);
            try {
                String response = PFNetworkManager.doPinnedGetForUrl(url);
                List<PharmacyType> pharmacies = gson.fromJson(response, PHARMACY_LIST_TYPE);
                Observable.from(pharmacies).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.w(e, "Could not process pharmacy response.");
                subscriber.onError(e);
            }
        });
    }


}
