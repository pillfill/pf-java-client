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
package com.apothesource.pillfill.service.prescription.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.aggregation.AccountAggregationTaskRequest;
import com.apothesource.pillfill.datamodel.aggregation.AccountAggregationTaskResponse;
import com.apothesource.pillfill.datamodel.aggregation.Point;
import com.apothesource.pillfill.datamodel.userdatatype.Credential;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.prescription.PrescriptionService;
import com.apothesource.pillfill.service.PFServiceEndpoints;

import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;

import com.google.common.base.Joiner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;
import rx.exceptions.OnErrorThrowable;
import timber.log.Timber;

/**
 * Created by Michael Ramirez on 5/29/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultPrescriptionServiceImpl implements PrescriptionService {
    private static final Logger log = Logger.getLogger("RxServiceImpl");
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    public static final Type RX_LIST_TYPE = new TypeToken<ArrayList<PrescriptionType>>() {
    }.getType();
    private static final String HTTP_CONTENT_TYPE_JSON = "application/json";
    public static final int EXTRACT_TIMEOUT_SECONDS = 60;
    public static final int EXTRACT_POLL_TIME_SECONDS = 2;

    @Override
    public Observable<PrescriptionType> getPrescription(String rxId) {
        return getPrescriptions(Collections.singletonList(rxId));
    }

    @Override
    public Observable<PrescriptionType> getPrescriptionWithRevId(String rxId, String revId) {
        throw new UnsupportedOperationException("Not implmenented yet");
    }

    @Override
    public Observable<PrescriptionType> getPrescriptions(List<String> rxIds) {
        return subscribeIoObserveImmediate(subscriber -> {
            if (rxIds == null || rxIds.isEmpty()) {
                log.warning("Cannot load prescriptions- empty id list");
                subscriber.onCompleted();
            } else {
                int idx = 0;
                while (idx < rxIds.size()) {
                    int batchSize = (rxIds.size() - idx > 100) ? 100 : rxIds.size() - idx;
                    List<String> reqList = rxIds.subList(idx, idx + batchSize);
                    final String url = String.format(PFServiceEndpoints.PRESCRIPTIONS_URL,
                            Joiner.on(",").skipNulls().join(reqList));
                    log.info(String.format("Requesting url: %s", url));
                    try {
                        OkHttpClient connection = PFNetworkManager.getPinnedPFHttpClient();
                        Request request = new Request.Builder()
                                .url(url)
                                .addHeader("Cache-Control", "no-cache")
                                .build();

                        Response response = connection.newCall(request).execute();

                        if (response.code() != 200) {
                            subscriber.onError(
                                    new RuntimeException(String.format("Invalid response code: %d", response.code())));
                            return;
                        } else {
                            Type type = new TypeToken<ArrayList<PrescriptionType>>() {
                            }.getType();
                            ArrayList<PrescriptionType> retList = gson.fromJson(response.body().string(), type);
                            Observable.from(retList).forEach(subscriber::onNext);
                        }
                    } catch (Exception e) {
                        subscriber.onError(e);
                        return;
                    }
                    idx += batchSize;
                }
                subscriber.onCompleted();
            }
        });
    }

    public Observable<AccountAggregationTaskResponse> requestPrescriptionExtraction(Credential c, Point location) {
        AccountAggregationTaskRequest request = new AccountAggregationTaskRequest();
        request.datasource = c.getSource();
        request.username = c.getPassword();
        request.location = location;
        request.dob = c.getDob();
        request.password = c.getPassword();
        return requestPrescriptionExtraction(request);
    }

    public Observable<AccountAggregationTaskResponse> requestPrescriptionExtraction(AccountAggregationTaskRequest request) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
                Request req = new Request.Builder()
                        .post(RequestBody.create(MediaType.parse(HTTP_CONTENT_TYPE_JSON), gson.toJson(request)))
                        .url(PFServiceEndpoints.RX_REQUEST_EXTRACT_URL)
                        .build();

                Response res = client.newCall(req).execute();
                AccountAggregationTaskResponse response = gson.fromJson(res.body().charStream(), AccountAggregationTaskResponse.class);
                subscriber.onNext(response);
                subscriber.onCompleted();
            } catch (IOException ex) {
                Logger.getLogger(DefaultPrescriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                subscriber.onError(ex);
            }
        });
    }

    public Observable<AccountAggregationTaskResponse> getExtractResponse(AccountAggregationTaskResponse task) {
        return subscribeIoObserveImmediate(subscriber -> {
            OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
            String statusUrl = String.format(PFServiceEndpoints.RX_EXTRACT_STATUS_URL, task.taskId);
            Request req = new Request.Builder()
                    .url(statusUrl)
                    .build();
            try {
                Response res = client.newCall(req).execute();
                AccountAggregationTaskResponse response = gson.fromJson(res.body().charStream(), AccountAggregationTaskResponse.class);
                subscriber.onNext(response);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e, "Error requesting extract status from server.");
                subscriber.onError(e);
            }
        });
    }

    public Observable<AccountAggregationTaskResponse> waitForExtractResponse(AccountAggregationTaskResponse task) {
        return waitForExtractResponse(task, EXTRACT_TIMEOUT_SECONDS);
    }
    public Observable<AccountAggregationTaskResponse> waitForExtractResponse(AccountAggregationTaskResponse task, int timeoutSeconds) {
        return Observable.interval(EXTRACT_POLL_TIME_SECONDS, TimeUnit.SECONDS).timeInterval()
                .flatMap(time -> {
                    AccountAggregationTaskResponse response = getExtractResponse(task).toBlocking().first();
                    if (response.resultCode != AccountAggregationTaskResponse.RESULT_CODE_PROCESSING) {
                        return Observable.just(response);
                    } else {
                        return Observable.empty();
                    }
                }).timeout(timeoutSeconds, TimeUnit.SECONDS).first();
    }

    @Override
    public Observable<PrescriptionType> enrichPrescriptions(PrescriptionType... rxList) {
        return enrichPrescriptions(Arrays.asList(rxList));
    }

    @Override
    public Observable<PrescriptionType> enrichPrescriptions(List<PrescriptionType> rxList) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
                Request req = new Request.Builder()
                        .post(RequestBody.create(MediaType.parse(HTTP_CONTENT_TYPE_JSON), gson.toJson(rxList)))
                        .url(PFServiceEndpoints.RX_ENRICH_URL)
                        .build();
                Response res = client.newCall(req).execute();
                List<PrescriptionType> enrichedRxList = gson.fromJson(res.body().charStream(), RX_LIST_TYPE);
                Observable.from(enrichedRxList).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException ex) {
                Logger.getLogger(DefaultPrescriptionServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                subscriber.onError(ex);
            }
        });
    }
}
