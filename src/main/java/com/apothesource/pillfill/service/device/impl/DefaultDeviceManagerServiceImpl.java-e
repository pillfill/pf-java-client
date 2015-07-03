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
package com.apothesource.pillfill.service.device.impl;

import com.apothesource.pillfill.datamodel.UserDevice;
import com.apothesource.pillfill.exception.UserDeviceResponseException;
import com.apothesource.pillfill.service.device.DeviceManagerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.apothesource.pillfill.service.PFServiceEndpoints;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rx.Observable;

import static com.apothesource.pillfill.datamodel.UserDevice.UserDeviceWSResponse;
import com.apothesource.pillfill.network.PFNetworkManager;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;
import com.google.common.base.Joiner;
import timber.log.Timber;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultDeviceManagerServiceImpl implements DeviceManagerService {

    private final OkHttpClient mHttpClient;
    private final Gson mGson;
    private final Type TYPE_DEVICE_LIST = new TypeToken<List<UserDevice>>() {}.getType();

    public DefaultDeviceManagerServiceImpl() {
        this.mHttpClient = PFNetworkManager.getPinnedPFHttpClient();
        this.mGson = new GsonBuilder().create();
    }

    @Override
    public Observable<UserDevice> getDeviceWithUUID(String... uuids) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String url = String.format(PFServiceEndpoints.DEVICE_BASE_URL, Joiner.on(",").join(uuids));
                Request.Builder builder = new Request.Builder().url(url).get();
                Response response = mHttpClient.newCall(builder.build()).execute();
                String responseJson = response.body().string();
                List<UserDevice> deviceList = mGson.fromJson(responseJson, TYPE_DEVICE_LIST);
                Observable.from(deviceList).toBlocking().forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e(e,"Failed to update reminders.");
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<UserDeviceWSResponse> addUpdateDevice(UserDevice userDevice, String deviceOwnerName) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String deviceJson = mGson.toJson(userDevice);
                RequestBody reminderJson = RequestBody.create(MediaType.parse("application/json"), deviceJson);
                String url = String.format(PFServiceEndpoints.DEVICES_BASE_URL, "test");
                Request.Builder builder = new Request.Builder().url(url).post(reminderJson);
                Response response = mHttpClient.newCall(builder.build()).execute();
                String responseJson = response.body().string();
                UserDeviceWSResponse wsResponse = mGson.fromJson(responseJson, UserDeviceWSResponse.class);
                if (wsResponse.error == null) {
                    subscriber.onNext(wsResponse);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new UserDeviceResponseException(wsResponse.error));
                }
            } catch (IOException e) {
                Timber.e(e,"Failed to update reminders.");
                subscriber.onError(e);
            }
        });
    }
}
