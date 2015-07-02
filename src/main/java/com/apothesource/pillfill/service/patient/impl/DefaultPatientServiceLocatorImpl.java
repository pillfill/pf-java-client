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
package com.apothesource.pillfill.service.patient.impl;

import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.service.patient.PatientService;
import com.apothesource.pillfill.service.patient.PatientServiceLocator;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultPatientServiceLocatorImpl implements PatientServiceLocator {
    private static final int MAX_CONCURRENT_PATIENTS = 5;
    private final ConcurrentHashMap<String, PatientService> patientServiceMap = new ConcurrentHashMap<>();

    @Override
    public Observable<PatientService> initializePatientService(AuthToken authToken, String password) {
        PatientService pds;
        if (!patientServiceMap.containsKey(authToken.getEmail())) {
            pds = new DefaultPatientServiceImpl();
            patientServiceMap.put(authToken.getEmail(), pds);
            return pds.init(authToken, password).map(pt -> pds);
        } else {
            Timber.d("Not initializing patient %s - Already initialized.",authToken.getEmail());
            pds = patientServiceMap.get(authToken.getEmail());
        }
        return Observable.just(pds);
    }


    @Override
    public void releasePatientDataService(String id) {
        Timber.i("Releasing patient service: %s", id);
        synchronized (patientServiceMap) {
            patientServiceMap.remove(id);
        }
    }


    @Override
    public Collection<PatientService> getCurrentPatients() {
        return patientServiceMap.values();
    }

    @Override
    public PatientService getPatientService(String ptId) {
        return patientServiceMap.get(ptId);
    }

    @Override
    public boolean canAddAnotherUser() {
        return patientServiceMap.size() < MAX_CONCURRENT_PATIENTS;
    }

}
