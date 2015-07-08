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
package com.apothesource.pillfill.service.prescription;

import com.apothesource.pillfill.datamodel.PrescriptionType;

import java.util.List;

import com.apothesource.pillfill.datamodel.aggregation.AccountAggregationTaskRequest;
import com.apothesource.pillfill.datamodel.aggregation.AccountAggregationTaskResponse;
import com.apothesource.pillfill.datamodel.aggregation.Point;
import com.apothesource.pillfill.datamodel.userdatatype.Credential;
import rx.Observable;

/**
 * Created by Michael Ramirez on 5/29/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public interface PrescriptionService {
    /**
     * A convenience method for {@link }#getPrescriptions)
     */
    Observable<PrescriptionType> getPrescription(String rxId);

    /**
     * Simple method to retrieve {@link PrescriptionType}s based on the generated Rx UUID.
     * @param rxIds A list of IDs representing the prescriptions to retrieve
     * @return A Stream of prescriptions associated with the provide id list
     */
    Observable<PrescriptionType> getPrescriptions(List<String> rxIds);

    /**
     * A convenience method for {@link #enrichPrescriptions(List)})
     */
    Observable<PrescriptionType> enrichPrescriptions(PrescriptionType... rxList);

    /**
     * Sends de-identified prescription information to the PF server to be normalized and linked.
     *
     * @param rxList The list of prescriptions to be updated. Must contain at least {@link PrescriptionType#dispenseDate},
     *               {@link PrescriptionType#source},{@link PrescriptionType#medicationName}, {@link PrescriptionType#quantity},
     *               and {@link PrescriptionType#uuid}. For accuracy, at least one of {@link PrescriptionType#ndc} or
     *               {@link PrescriptionType#rxNormId} shoudl be set.
     * @return A stream of updated prescriptions.
     */
    Observable<PrescriptionType> enrichPrescriptions(List<PrescriptionType> rxList);

    /**
     * Sends the provided account information to the PF server to preform an health/Rx record extraction. A short-lived is
     * instantiated just long enough to preform the data Rx extraction work. Once completed, all sensitive information is
     * is purged along with the worker itself, only the de-identified prescription data is retained.
     *
     * @param request A request to extract health/rx information with the provided information
     * @return A stream of responses, normally acknowledging the receipt of the request
     */
    Observable<AccountAggregationTaskResponse> requestPrescriptionExtraction(AccountAggregationTaskRequest request);


    /**
     * A convenience method for {@link #requestPrescriptionExtraction(AccountAggregationTaskRequest)}.
     */
    Observable<AccountAggregationTaskResponse> requestPrescriptionExtraction(Credential c, Point location);

    /**
     * Once the extraction has been requested, this method is used to poll for updates (using the {@link AccountAggregationTaskResponse#taskId})
     * which will return a list of Prescription IDs once completed.
     * @param task The taskId from the original {@link AccountAggregationTaskRequest}
     * @return A stream of responses that will provide the latest status of the extraction process
     */
    Observable<AccountAggregationTaskResponse> getExtractResponse(AccountAggregationTaskResponse task);
}
