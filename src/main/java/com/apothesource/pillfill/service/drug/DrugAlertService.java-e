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
package com.apothesource.pillfill.service.drug;

import com.apothesource.pillfill.datamodel.DrugAlertType;
import com.apothesource.pillfill.datamodel.PrescriptionType;

import java.util.Collection;

import rx.Observable;

/**
 * Created by Michael Ramirez on 6/1/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 * 
 * You must set the pfApiKey system property to use this service.
 */
public interface DrugAlertService {
    /**
     * A convenience method to check for all alert types simultaneously. 
     * @param activeRxList The list of active prescriptions for the user.
     * @param weightInKgs The weight of the user in kilograms.
     * @return A stream of DrugAlerts that applies to the patient.
     */
    Observable<DrugAlertType> checkForAllAlerts(Collection<PrescriptionType> activeRxList, float weightInKgs);

    /**
     * This checks for potential overdose per the FDA's Maximum Recommended Therapeutic Dose (MRTD) values.
     * @param activeRxList The list of active prescriptions for the user.
     * @param weightInKgs The weight of the user in kilograms.
     * @return A stream of DrugAlerts that applies to the patient.
     */
    Observable<DrugAlertType> checkForDrugDoseWarnings(Collection<PrescriptionType> activeRxList, float weightInKgs);

    /**
     * This checks the server to see if any of the provided prescriptions have been affected by an FDA safety action (recall or shortage)
     * @param rxList The list of active prescriptions for the user.
     * @return A stream of DrugAlerts that applies to the patient.
     */
    Observable<DrugAlertType> checkForDrugAlerts(Collection<PrescriptionType> rxList);

    /**
     * This checks for potential interactions between the various drugs in the active list.
     * @param rxList The list of active prescriptions for the user.
     * @return A stream of DrugAlerts that applies to the patient.
     */
    Observable<DrugAlertType> checkForRxNormInteractions(Collection<PrescriptionType> rxList);
}
