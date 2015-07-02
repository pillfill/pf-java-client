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

import com.apothesource.pillfill.datamodel.PatientType;
import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriberType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.UserDataType;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.datamodel.android.SecurePatientTypeWrapper;
import com.apothesource.pillfill.datamodel.android.exception.EncryptionException;
import com.apothesource.pillfill.exception.PFMasterPasswordException;
import com.apothesource.pillfill.service.patient.PatientService;
import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by Michael Ramirez on 6/2/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public interface PatientServiceImpl extends PatientService {
    void addPrescription(PrescriptionType rx);

    void addAllPrescriptions(List<PrescriptionType> rx);

    void removePrescription(PrescriptionType rx);

    void removeAllPrescriptions(List<PrescriptionType> rx);

    void addPrescriber(PrescriberType dr);

    void addAllPrescribers(List<PrescriberType> dr);

    void removePrescriber(PrescriberType dr);

    void removeAllPrescribers(List<PrescriberType> dr);

    void addPharmacy(PharmacyType dr);

    void addAllPharmacies(List<PharmacyType> dr);

    void removePharmacy(PharmacyType dr);

    void removeAllPharmacies(List<PharmacyType> dr);

    PrescriptionType getPrescription(String uuid);

    AuthToken getAuthToken();

    PatientType getPatientData();

    boolean getNeedsServerUpdate();

    void setNeedsServerUpdate(boolean needsServerUpdate);

    UserDataType getUserData();

    void setUserData(UserDataType userData);

    List<String> getPrescriptionIds();

    void openPatientDocument(char[] password) throws PFMasterPasswordException;

    String getCurrentRevision();

    void updateDrugAlerts();

    void setPatientDocument(JsonElement closedPtDoc);

    SecurePatientTypeWrapper getClosedPatientDocument() throws EncryptionException;
}
