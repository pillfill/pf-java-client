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
package com.apothesource.pillfill.service.reminder.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.Reminder;
import com.apothesource.pillfill.datamodel.Reminder.ReminderWSResponse;
import com.apothesource.pillfill.exception.ReminderConfigurationException;
import com.apothesource.pillfill.network.PillFillSSLSocketFactory;
import com.apothesource.pillfill.service.patient.PatientServiceParams;
import com.apothesource.pillfill.service.reminder.ReminderService;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import rx.Observable;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultReminderServiceImpl implements ReminderService {

    private final OkHttpClient mHttpClient;
    private final Gson mGson;
    private final Logger mLog = Logger.getLogger("DefaultReminderSvcImpl");

    public DefaultReminderServiceImpl() {
        this.mHttpClient = PillFillSSLSocketFactory.getPinnedPFHttpClient();
        this.mGson = new GsonBuilder().create();
    }

    @Override
    public Observable<ReminderWSResponse> updateRemindersForRx(List<Reminder> reminderList, PrescriptionType rx) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                RequestBody reminderJson = RequestBody.create(MediaType.parse("application/json"), mGson.toJson(reminderList));
                String url = String.format(PatientServiceParams.REMINDER_BASE_URL, rx.getUuid());
                Request.Builder builder = new Request.Builder().url(url).post(reminderJson);
                Response response = mHttpClient.newCall(builder.build()).execute();
                String responseJson = response.body().string();
                ReminderWSResponse wsResponse = mGson.fromJson(responseJson, ReminderWSResponse.class);
                subscriber.onNext(wsResponse);
                subscriber.onCompleted();
            } catch (IOException e) {
                mLog.log(Level.SEVERE, "Failed to update reminders.", e);
                subscriber.onError(e);
            }
        });
    }

    @Override
    public Observable<Reminder> getRemindersForRx(PrescriptionType rx) {
        return subscribeIoObserveImmediate(subscriber -> {
            try {
                String url = String.format(PatientServiceParams.REMINDER_BASE_URL, rx.getUuid());
                Request.Builder builder = new Request.Builder().url(url).get();
                Response response = mHttpClient.newCall(builder.build()).execute();
                String responseJson = response.body().string();
                ReminderWSResponse wsResponse = mGson.fromJson(responseJson, ReminderWSResponse.class);
                if (wsResponse.error == null) {
                    Observable.from(wsResponse.reminders).toBlocking().forEach(subscriber::onNext);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new ReminderConfigurationException(wsResponse.error));
                }
            } catch (IOException e) {
                mLog.log(Level.SEVERE, "Failed to update reminders.", e);
                subscriber.onError(e);
            }
        });
    }
}
