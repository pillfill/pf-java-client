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
package com.apothesource.pillfill.service.patient.command;

import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriberType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.UserDataType;
import com.apothesource.pillfill.datamodel.android.PFPatientSync;
import com.apothesource.pillfill.datamodel.android.SecurePatientTypeWrapper;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.patient.PatientService;
import com.apothesource.pillfill.service.patient.impl.PatientServiceImpl;
import com.apothesource.pillfill.service.PFServiceEndpoints;

import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;


import rx.Observable;

/**
 * Created by Michael Ramirez on 5/28/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class ModifyPatientActions {

    public enum ModificationType {
        CHANGE_ADD, CHANGE_REMOVE, CHANGE_UPDATE, CHANGE_SYNC
    }

    public enum ChangeState {
        CHANGE_NEW, CHANGE_COMMITTED, CHANGE_UNDONE
    }

    @SuppressWarnings("unchecked")
    public static abstract class PatientCommand<T extends PatientCommand<T>> implements Serializable {
        protected transient PatientServiceImpl mPatientSvc;
        protected transient Logger log = Logger.getLogger(this.getClass().getName());

        public T patientService(PatientService ptService) {
            if (!(ptService instanceof PatientServiceImpl)) {
                throw new IllegalArgumentException("Patient Service must implement PatientServiceImpl interface.");
            } else {
                this.mPatientSvc = (PatientServiceImpl) ptService;
            }
            return (T) this;

        }

        public abstract Observable<ExecutionResult> doAction();
    }

    @SuppressWarnings("unchecked")
    public static abstract class ModifyPatientCommand<S extends ModifyPatientCommand<S>> extends PatientCommand {
        private static final String UNKNOWN_REV = "UNKNOWN";
        protected ModificationType mChangeType = ModificationType.CHANGE_SYNC;
        protected ChangeState mChangeState = ChangeState.CHANGE_NEW;
        private String mRevId = UNKNOWN_REV;
        private String uuid = UUID.randomUUID().toString();
        private ChangeState changeState;

        public abstract Observable<ExecutionResult> undoAction();

        public void setChangeType(ModificationType changeType) {
            this.mChangeType = changeType;
        }

        public void setRevisionId(String revisionId) {
            this.mRevId = revisionId;
        }

        public boolean isCommitted() {
            return mRevId != null;
        }

        public S patientService(PatientService ptSvc) {
            return (S) super.patientService(ptSvc);
        }

        public S modificationType(ModificationType mt) {
            mChangeType = mt;
            return (S) this;
        }

        @Override
        public int hashCode() {
            return uuid.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null){
                return false;
            }
            if(!(obj instanceof ModifyPatientCommand)){
                return false;
            }

            return ((ModifyPatientCommand)(obj)).uuid.equals(this.uuid);
        }

        public void setChangeState(ChangeState changeState) {
            this.changeState = changeState;
        }
    }

    public static class ModifyUserAction extends ModifyPatientCommand {
        private UserDataType newUserData;
        private UserDataType oldUserData;

        @Override
        public Observable<ExecutionResult> doAction() {
            assert (mPatientSvc != null);
            assert (mChangeState == ChangeState.CHANGE_NEW);
            return Observable.create(subscriber -> {
                oldUserData = mPatientSvc.getUserData();
                mPatientSvc.getPatientData().setUserData(newUserData);
                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                subscriber.onCompleted();
            });
        }

        @Override
        public Observable<ExecutionResult> undoAction() {
            return Observable.create(subscriber -> {
                mPatientSvc.getPatientData().setUserData(oldUserData);
                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                subscriber.onCompleted();
            });
        }

        public void setOldUserData(UserDataType oldUserData) {
            this.oldUserData = oldUserData;
        }

        public void setNewUserData(UserDataType newUserData) {
            this.newUserData = newUserData;
        }
    }

    public static class ModifyRxListAction extends ModifyPatientCommand<ModifyRxListAction> {
        private List<PrescriptionType> mRxList = new ArrayList<>();
        private List<PrescriptionType> mPrevRxList = new ArrayList<>();

        @Override
        public Observable<ExecutionResult> doAction() {
            return doAction(false);
        }

        @Override
        public Observable<ExecutionResult> undoAction() {
            return doAction(true);
        }

        private Observable<ExecutionResult> doAction(boolean isUndo) {
            assert (mPatientSvc != null);
            return Observable.create(subscriber -> {
                switch (mChangeType) {
                    case CHANGE_UPDATE:
                        //Fall through - Add/Update are the same
                    case CHANGE_ADD: {
                        //Check and save the previous version first then add new Rxs.
                        if (!isUndo) {
                            assert (mChangeState == ChangeState.CHANGE_NEW);
                            Observable.from(mRxList).forEach(rx -> {
                                PrescriptionType currentRx = mPatientSvc.getPrescription(rx.getUuid());
                                if (currentRx != null) mPrevRxList.add(currentRx);
                                mPatientSvc.addPrescription(rx);
                            });
                        } else {
                            mPatientSvc.removeAllPrescriptions(mRxList);
                            mPatientSvc.addAllPrescriptions(mPrevRxList);
                        }
                        break;
                    }
                    case CHANGE_REMOVE: {
                        if (!isUndo) {
                            assert (mChangeState == ChangeState.CHANGE_NEW);
                            Observable.from(mRxList).forEach(rx -> {
                                PrescriptionType currentRx = mPatientSvc.getPrescription(rx.getUuid());
                                if (currentRx != null) mPrevRxList.add(currentRx);
                                mPatientSvc.removePrescription(rx);
                            });
                        } else {
                            mPatientSvc.addAllPrescriptions(mPrevRxList);
                        }
                        break;
                    }
                }
                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                subscriber.onCompleted();
            });
        }

        public ModifyRxListAction prescriptions(List<PrescriptionType> rxList) {
            mRxList = rxList;
            return this;
        }

        public void addPrescription(PrescriptionType rx) {
            mRxList.add(rx);
        }

    }

    public static class ModifyDrListAction extends ModifyPatientCommand {
        private PrescriberType mNewDr;
        private PrescriberType mOldDr;


        @Override
        public Observable<ExecutionResult> doAction() {
            return doAction(false);
        }

        @Override
        public Observable<ExecutionResult> undoAction() {
            return doAction(true);
        }

        private Observable<ExecutionResult> doAction(boolean isUndo) {
            assert (mPatientSvc != null);
            return Observable.create(subscriber -> {
                switch (mChangeType) {
                    case CHANGE_ADD: {
                        if (!isUndo) mPatientSvc.addPrescriber(mNewDr);
                        else mPatientSvc.removePrescriber(mNewDr);
                        break;
                    }
                    case CHANGE_REMOVE: {
                        if (!isUndo) mPatientSvc.removePrescriber(mNewDr);
                        else mPatientSvc.addPrescriber(mNewDr);
                        break;
                    }
                    case CHANGE_UPDATE: {
                        PrescriberType changeDr = isUndo ? mOldDr : mNewDr;
                        mPatientSvc.removePrescriber(changeDr);
                        mPatientSvc.addPrescriber(changeDr);
                        break;
                    }
                }
                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                subscriber.onCompleted();
            });
        }

        public PrescriberType getmNewDr() {
            return mNewDr;
        }

        public void setmNewDr(PrescriberType mNewDr) {
            this.mNewDr = mNewDr;
        }

        public PrescriberType getmOldDr() {
            return mOldDr;
        }

        public void setmOldDr(PrescriberType mOldDr) {
            this.mOldDr = mOldDr;
        }

    }

    public static class ModifyPharmListAction extends ModifyPatientCommand {
        private PharmacyType newPharm;
        private PharmacyType oldPharm;


        @Override
        public Observable<ExecutionResult> doAction() {
            return doAction(false);
        }

        @Override
        public Observable<ExecutionResult> undoAction() {
            return doAction(true);
        }

        private Observable<ExecutionResult> doAction(boolean isUndo) {
            return Observable.create(subscriber -> {
                switch (mChangeType) {
                    case CHANGE_ADD: {
                        if (!isUndo) mPatientSvc.addPharmacy(newPharm);
                        else mPatientSvc.removePharmacy(newPharm);
                        break;
                    }
                    case CHANGE_REMOVE: {
                        if (!isUndo) mPatientSvc.removePharmacy(newPharm);
                        else mPatientSvc.addPharmacy(newPharm);
                        break;
                    }
                    case CHANGE_UPDATE: {
                        PharmacyType changePharm = isUndo ? oldPharm : newPharm;
                        mPatientSvc.removePharmacy(changePharm);
                        mPatientSvc.addPharmacy(changePharm);
                        break;
                    }
                }
                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                subscriber.onCompleted();
            });
        }

        public void setNewPharm(PharmacyType newPharm) {
            this.newPharm = newPharm;
        }

        public void setOldPharm(PharmacyType oldPharm) {
            this.oldPharm = oldPharm;
        }
    }

    public static class UpdatePatientOnServerAction extends ModifyPatientCommand {
        final Gson gson;
        final Logger log = Logger.getLogger("UpdatePatientOnServerAction");
        private String mUpdateUrl;

        public UpdatePatientOnServerAction() {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        }

        @Override
        public Observable<ExecutionResult> doAction() {
            mUpdateUrl = String.format(PFServiceEndpoints.PATIENT_UPDATE_URL, mPatientSvc.getAuthToken().getEmail());
            return subscribeIoObserveImmediate(subscriber -> {
                log.fine("Syncing with server…");
                try {
                    SecurePatientTypeWrapper secPatientData = mPatientSvc.getClosedPatientDocument();

                    String patientData = gson.toJson(secPatientData);
                    if (patientData == null || patientData.isEmpty())
                        throw new RuntimeException("Patient document is null.");
                    
                    OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
                    
                    RequestBody reqBody = RequestBody.create(MediaType.parse("application/json"), patientData);
                    
                    Request request = new Request.Builder()
                            .url(mUpdateUrl)
                            .method("PUT", reqBody)
                            .addHeader("Bearer", mPatientSvc.getAuthToken().toAuthTokenHeaderString())
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        String body = response.body().string();
                        int statusCode = response.code();

                        log.fine("Server responded with status: " + statusCode);
                        log.fine("Detail: " + body);

                        PFPatientSync.PFSyncResponse pfresponse = PFPatientSync.PFSyncResponse.parseResponse(body);

                        if (pfresponse.success) {
                            try {
                                String revision = pfresponse.result;
                                mPatientSvc.getPatientData().set_rev(revision); //Take the revision from the synced version
                                log.fine("Updated patientService document to revision: " + mPatientSvc.getPatientData().get_rev());
                                subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                                subscriber.onCompleted();
                            } catch (Exception e) {
                                log.severe("Error trying to parse response from server: " + e.toString());
                                subscriber.onError(new IOException("Error syncing to server."));
                            }
                        } else {
                            subscriber.onError(new RuntimeException(pfresponse.reason));
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Encoding exception.", e);
                        subscriber.onError(e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            });
        }

        @Override
        public Observable<ExecutionResult> undoAction() {
            throw new UnsupportedOperationException("Cannot undo sync.");
        }
    }
}
