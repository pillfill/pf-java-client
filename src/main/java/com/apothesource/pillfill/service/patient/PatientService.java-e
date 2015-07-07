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

import com.apothesource.pillfill.datamodel.DrugAlertType;
import com.apothesource.pillfill.datamodel.PatientType;
import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriberType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.service.patient.command.ExecutionResult;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.ModifyPatientCommand;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.PatientCommand;

import rx.Observable;

public interface PatientService {
    /**
     * Used to initialize the patient service with a provided {@link AuthToken}.
     * @param at The authenticationToken to use
     * @param password The user's password to decrypt the patient document
     * @return A stream that will return the Patient document once decrypted
     */
    Observable<PatientType> init(AuthToken at, String password);

    /**
     * Get the the Patient document
     * @return The patient document
     */
    Observable<PatientType> getPatient();

    /**
     * Get all prescriptions related to this patient
     * @return A stream of Rxs for this patient
     */
    Observable<PrescriptionType> getAllPrescriptions();

    /**
     * Get only the prescriptions that are currently active (defined as having a {@link PrescriptionType#getComputedInactiveAfterDate()} after today
     * @return A stream of active Rxs for this patient
     */
    Observable<PrescriptionType> getActivePrescriptions();

    /**
     * Get only the prescriptions that are currently inactive (defined as having a {@link PrescriptionType#getComputedInactiveAfterDate()} before today
     * @return A stream of active Rxs for this patient
     */
    Observable<PrescriptionType> getInactivePrescriptions();

    /**
     * Get all drug alerts for any currently active prescription for this patient
     * @return A stream of DrugAlerts related to this patient and active medications
     */
    Observable<DrugAlertType> getDrugAlerts();

    /**
     * Get all prescribers for this patient (associated with either active or inactive prescriptions).
     * @return A stream of Prescribers for this patient
     */
    Observable<PrescriberType> getPrescribers();

    /**
     * Get all pharmacies for this patient (associated with either active or inactive prescriptions).
     * @return A stream of Pharmacies associated with this patient
     */
    Observable<PharmacyType> getPharmacies();

    /**
     * Apply a {@link PatientCommand} to this patient. You can used this to modify a patient through {@link ModifyPatientCommand}.
     * @param e The command to apply to this patient
     * @return The result of
     */
    Observable<ExecutionResult> doAction(PatientCommand e);

    /**
     * Undo a {@link ModifyPatientCommand} action on a Patient.
     * @param e The command to undo
     * @return The outcome of the undo action
     */
    Observable<ExecutionResult> undoAction(ModifyPatientCommand e);

    /**
     * Commits the changes from each uncommitted {@link ModifyPatientCommand} command to the server
     * @return The outcome of the commit action
     */
    Observable<ExecutionResult> commitToServer();

}
