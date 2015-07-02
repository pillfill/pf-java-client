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
package com.apothesource.pillfill.service.patient;

import com.apothesource.pillfill.datamodel.android.AuthToken;

import java.util.Collection;

import rx.Observable;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public interface PatientServiceLocator {
    /**
     * Initializes the patient service with the provided token and then adds it to the service locator
     * @param authToken
     * @param password
     * @return 
     */
    Observable<PatientService> initializePatientService(AuthToken authToken, String password);

    /**
     * Instructs the locator to release the identified Patient Service
     * @param id The ID of the service to release (email of the {@link AuthToken})
     * @see {@link AuthToken#email}
     */
    void releasePatientDataService(String id);

    /**
     * Get all patient services currently registered with this locator
     * @return A list of {@link PatientService}s held by this locator
     */
    Collection<PatientService> getCurrentPatients();

    /**
     * Get a specific {@link PatientService} by ID
     * @param ptId The patient service ID to retrieve
     * @return The identified patient service (or null if it doesn't exist)
     * @see {@link AuthToken#email}
     */
    PatientService getPatientService(String ptId);

    boolean canAddAnotherUser();
}
