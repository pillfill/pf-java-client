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
package com.apothesource.pillfill.service.patient.impl;

import com.apothesource.pillfill.datamodel.DrugAlertType;
import com.apothesource.pillfill.datamodel.PatientType;
import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriberType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.UserDataType;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.datamodel.android.SecurePatientTypeWrapper;
import com.apothesource.pillfill.datamodel.android.exception.EncryptionException;
import com.apothesource.pillfill.exception.InvalidPrescriptionIdException;
import com.apothesource.pillfill.exception.PFMasterPasswordException;
import com.apothesource.pillfill.exception.PFBadTokenException;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.drug.DrugAlertService;
import com.apothesource.pillfill.service.drug.impl.DefaultDrugAlertServiceImpl;
import com.apothesource.pillfill.service.patient.command.ExecutionResult;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.ModifyPatientCommand;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.PatientCommand;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.UpdatePatientOnServerAction;
import com.apothesource.pillfill.service.PFServiceEndpoints;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;
import com.google.common.base.Joiner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by rammic on 5/28/15.
 */
public class DefaultPatientServiceImpl implements PatientServiceImpl {
    private static final Type RX_LIST_TYPE = new TypeToken<List<PrescriptionType>>() {
    }.getType();
    private final Gson mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    private final Map<String, PrescriptionType> mRxMap = new HashMap<>();
    private final Map<String, PrescriberType> mDrMap = new HashMap<>();
    private final Map<String, PharmacyType> mPharmMap = new HashMap<>();
    public static final String STATUS_NOT_INITIALIZED = "NOT_INIT";
    private SecurePatientTypeWrapper mPatient;
    private AuthToken mAuthToken;
    private char[] mPassword;
    private boolean mNeedsServerUpdate;
    private final HashSet<ModifyPatientCommand> uncommittedCommands = new HashSet<>();

    @Override
    public Observable<PatientType> init(AuthToken at, String password) {
        mPassword = password.toCharArray();
        mAuthToken = at;
        LoadPatientDataAction action = new LoadPatientDataAction()
                .patientService(this);
        return doAction(action).flatMap(executionResult -> {
            if (executionResult == ExecutionResult.RESULT_SUCCESS) {
                try {
                    openPatientDocument(mPassword);
                } catch (PFMasterPasswordException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new RuntimeException("Error getting patient doc.");
            }
            return Observable.just(mPatient);
        });
    }

    @Override
    public Observable<PatientType> getPatient() {
        return Observable.just(mPatient);
    }

    public void setPatient(SecurePatientTypeWrapper pt) {
        this.mPatient = pt;
    }

    @Override
    public Observable<PrescriptionType> getAllPrescriptions() {
        return Observable.from(mRxMap.values());
    }

    @Override
    public Observable<PrescriptionType> getActivePrescriptions() {
        Date now = new Date();
        return Observable.from(mRxMap.values()).filter(rx -> rx.getComputedInactiveAfterDate() == null || rx.getComputedInactiveAfterDate().after(now));
    }

    @Override
    public Observable<PrescriptionType> getInactivePrescriptions() {
        Date now = new Date();
        return Observable.from(mRxMap.values()).filter(rx -> rx.getComputedInactiveAfterDate() != null && rx.getComputedInactiveAfterDate().before(now));
    }

    @Override
    public Observable<DrugAlertType> getDrugAlerts() {
        return getActivePrescriptions().flatMap(rx -> Observable.from(rx.getDrugAlerts()));
    }

    @Override
    public Observable<PrescriberType> getPrescribers() {
        return Observable.from(mDrMap.values());
    }

    @Override
    public Observable<PharmacyType> getPharmacies() {
        return Observable.from(mPharmMap.values());
    }

    @Override
    public Observable<ExecutionResult> doAction(PatientCommand e) {
        if(e instanceof ModifyPatientCommand){
            uncommittedCommands.add((ModifyPatientCommand) e);
        }
        e.patientService(this);
        return e.doAction();
    }

    @Override
    public Observable<ExecutionResult> undoAction(ModifyPatientCommand e) {
        e.patientService(this);
        return e.undoAction();
    }

    @Override
    public Observable<ExecutionResult> commitToServer() {
        return doAction(new UpdatePatientOnServerAction()).doOnNext(executionResult -> {
            if(executionResult == ExecutionResult.RESULT_SUCCESS){
                for(ModifyPatientCommand cmd : uncommittedCommands){
                    cmd.setRevisionId(mPatient.get_rev());
                    cmd.setChangeState(ModifyPatientActions.ChangeState.CHANGE_COMMITTED);
                }
                uncommittedCommands.clear();
            }
        });
    }

    public void addPrescription(PrescriptionType rx) {
        mRxMap.put(rx.getUuid(), rx);
    }

    @Override
    public void addAllPrescriptions(List<PrescriptionType> rxList) {
        Observable.from(rxList).forEach(rx -> mRxMap.put(rx.getUuid(), rx));
    }

    @Override
    public void removePrescription(PrescriptionType rx) {
        removePrescription(rx.getUuid());
    }

    @Override
    public void removeAllPrescriptions(List<PrescriptionType> rxList) {
        Observable.from(rxList).forEach(rx -> {
            mRxMap.remove(rx.getUuid());
        });
    }

    public void removePrescription(String rxId) {
        mRxMap.remove(rxId);
    }

    @Override
    public void addPrescriber(PrescriberType dr) {
        mDrMap.put(dr.getNpi(), dr);
    }

    @Override
    public void addAllPrescribers(List<PrescriberType> drList) {
        Observable.from(drList).forEach(dr -> {
            mDrMap.put(dr.getNpi(), dr);
        });
    }

    @Override
    public void removePrescriber(PrescriberType dr) {
        removePrescriber(dr.getNpi());
    }

    @Override
    public void removeAllPrescribers(List<PrescriberType> drList) {
        Observable.from(drList).forEach(dr -> {
            mDrMap.remove(dr.getNpi());
        });
    }

    public void removePrescriber(String npi) {
        mDrMap.remove(npi);
    }

    @Override
    public void updateDrugAlerts() {
        doAction(new UpdateDrugAlertsAction()).subscribe(executionResult -> {
            Timber.d("Updated Drug Alerts. Result: %s", executionResult.name());
        });
    }

    @Override
    public void setPatientDocument(JsonElement closedPtDoc) {
        assert(closedPtDoc != null);
        mPatient = mGson.fromJson(closedPtDoc, SecurePatientTypeWrapper.class);
        assert(mPatient != null);
    }

    @Override
    public SecurePatientTypeWrapper getClosedPatientDocument() throws EncryptionException {
        JsonElement patientDocCopy = mGson.toJsonTree(getPatientData());
        SecurePatientTypeWrapper wrapper = mGson.fromJson(patientDocCopy, SecurePatientTypeWrapper.class);
        wrapper.closePatientDocument(mPassword);
        return wrapper;
    }

    @Override
    public void openPatientDocument(char[] password) throws PFMasterPasswordException {
        mPatient.openPatientDocument(mPassword);
    }

    @Override
    public String getCurrentRevision() {
        return mPatient == null ? STATUS_NOT_INITIALIZED : mPatient.get_rev();
    }

    @Override
    public boolean getNeedsServerUpdate() {
        return mNeedsServerUpdate;
    }

    @Override
    public void setNeedsServerUpdate(boolean mNeedsServerUpdate) {
        this.mNeedsServerUpdate = mNeedsServerUpdate;
    }

    @Override
    public UserDataType getUserData() {
        return mPatient.getUserData();
    }

    @Override
    public void setUserData(UserDataType userData) {
        mPatient.setUserData(userData);
    }

    @Override
    public void removePharmacy(PharmacyType changePharm) {
        mPharmMap.remove(changePharm.get_id());
    }

    @Override
    public void removeAllPharmacies(List<PharmacyType> phList) {
        Observable.from(phList).forEach(pharmacyType -> {
            mPharmMap.remove(pharmacyType.getUuid());
        });
    }

    @Override
    public PrescriptionType getPrescription(String uuid) {
        return mRxMap.get(uuid);
    }

    @Override
    public void addPharmacy(PharmacyType newPharm) {
        mPharmMap.put(newPharm.getUuid(), newPharm);
    }

    @Override
    public void addAllPharmacies(List<PharmacyType> phList) {
        Observable.from(phList).forEach(pharmacyType -> {
            mPharmMap.put(pharmacyType.getUuid(), pharmacyType);
        });

    }

    @Override
    public AuthToken getAuthToken() {
        return mAuthToken;
    }

    @Override
    public PatientType getPatientData() {
        return mPatient;
    }

    @Override
    public List<String> getPrescriptionIds() {
        List<String> rxIds = getPatientData().getRxList().getCurrentList();
        rxIds.addAll(getPatientData().getRxList().getInactiveList());
        return rxIds;
    }

    public static class LoadPatientDataAction extends PatientCommand<LoadPatientDataAction> {
        private String mUpdateUrl;

        @Override
        public Observable<ExecutionResult> doAction() {
            mUpdateUrl = String.format(PFServiceEndpoints.PATIENT_UPDATE_URL, mPatientSvc.getAuthToken().getEmail());
            return subscribeIoObserveImmediate(Observable.create(subscriber -> {
                Timber.d("Requesting url: %d", mUpdateUrl);
                try {
                    OkHttpClient okClient = PFNetworkManager.getPinnedPFHttpClient();
                    Request.Builder reqBuilder = new Request.Builder()
                            .url(mUpdateUrl)
                            .addHeader("Bearer", mPatientSvc.getAuthToken().toAuthTokenHeaderString())
                            .addHeader("Cache-Control", "no-cache");
                    Response response = okClient.newCall(reqBuilder.build()).execute();
                    if(response.code() == 200){
                        JsonParser parser = new JsonParser();
                        JsonElement doc = parser.parse(new InputStreamReader(response.body().byteStream()));
                        mPatientSvc.setPatientDocument(doc);
                        subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                        subscriber.onCompleted();
                    }else{
                        throw new PFBadTokenException(response.code(), "Unexpected response code from server: " + response.code());
                    }
                } catch (Exception e) {
                    Timber.e(e, "Error syncing profile to server: %s", e.getMessage());
                    subscriber.onError(e);
                }
            }));
        }
    }

    public static class LoadRxDataAction extends PatientCommand<LoadRxDataAction> {
        final Gson mGson;
        private final OkHttpClient mHttpClient;
        private List<String> mRxIdList;

        public LoadRxDataAction() {
            mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            mHttpClient = PFNetworkManager.getPinnedPFHttpClient();
        }

        public LoadRxDataAction prescriptionIds(List<String> rxIds) {
            mRxIdList = rxIds;
            return this;
        }

        public LoadRxDataAction prescriptionIds(String... rxIds) {
            return prescriptionIds(Arrays.asList(rxIds));
        }

        private void getAllPrescriptionIdsForPatient() {
            mRxIdList = mPatientSvc.getPrescriptionIds();
        }

        @Override
        public Observable<ExecutionResult> doAction() {
            //If we didn't get a specific load list, load all rxIds for this patient
            if (mRxIdList == null) getAllPrescriptionIdsForPatient();

            return subscribeIoObserveImmediate(Observable.create(subscriber -> {
                //We need to load in increments of 100 so break up the request if it's larger
                for (int i = 0; i < mRxIdList.size(); i += 100) {
                    List<String> rxIdSubset = mRxIdList.subList(i, (mRxIdList.size() >= i + 100) ? i + 100 : mRxIdList.size());
                    String rxIdsParams = Joiner.on(",").join(rxIdSubset);
                    String updateUrl = String.format(PFServiceEndpoints.PRESCRIPTIONS_URL, rxIdsParams);
                    try {
                        Request.Builder loadRequest = new Request.Builder().url(updateUrl);
                        Response loadResponse = mHttpClient.newCall(loadRequest.build()).execute();
                        String rxBody = loadResponse.body().string();
                        List<PrescriptionType> rxList = mGson.fromJson(rxBody, RX_LIST_TYPE);
                        mPatientSvc.addAllPrescriptions(rxList);
                        subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                    } catch (Exception e) {
                        Timber.e(e,"Error syncing profile to server.");
                        subscriber.onError(e);
                        return;
                    }
                }
                subscriber.onCompleted();
            }));
        }
    }

    public static class UpdateDrugAlertsAction extends PatientCommand<UpdateDrugAlertsAction> {
        @Inject
        DrugAlertService mDrugAlertSvc;
        private HashMap<String, PrescriptionType> mRxMap;

        @Override
        public Observable<ExecutionResult> doAction() {
            if (mRxMap == null) {
                setPrescriptions(mPatientSvc.getActivePrescriptions().toList().toBlocking().first());
            }
            return subscribeIoObserveImmediate(Observable.create(subscriber -> {
                Observable<DrugAlertType> fdaAlerts = mDrugAlertSvc.checkForDrugAlerts(mRxMap.values());
                Observable<DrugAlertType> mrtdAlerts = mDrugAlertSvc.checkForDrugDoseWarnings(mRxMap.values(), mPatientSvc.getUserData().getWeightInKgs());
                Observable<DrugAlertType> interactionAlerts = mDrugAlertSvc.checkForRxNormInteractions(mRxMap.values());

                for (PrescriptionType rx : mRxMap.values()) {
                    rx.getDrugAlerts().clear();
                }
                Observable.merge(fdaAlerts, mrtdAlerts, interactionAlerts).subscribe(drugAlert -> {
                    Observable.from(drugAlert.getRelatedRxIds()).forEach(rxId -> {
                        PrescriptionType rx = mPatientSvc.getPrescription(rxId);
                        if (rx != null) {
                            rx.getDrugAlerts().add(drugAlert);
                        } else {
                            Timber.w("Invalid RxId Included: %s", rxId);
                            subscriber.onError(new InvalidPrescriptionIdException(rxId));
                        }
                        subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                    });

                }, subscriber::onError, subscriber::onCompleted);
            }));
        }

        public UpdateDrugAlertsAction setPrescriptions(List<PrescriptionType> rxList) {
            mRxMap = new HashMap<>();
            Observable.from(rxList).forEach(rx -> {
                mRxMap.put(rx.getUuid(), rx);
            });
            return this;
        }

        public UpdateDrugAlertsAction drugAlertService(DefaultDrugAlertServiceImpl drugAlertService) {
            this.mDrugAlertSvc = drugAlertService;
            return this;
        }
    }

    public static class CheckServerVersionAction extends PatientCommand<CheckServerVersionAction> {
        private String mUpdateUrl;
        private String mCurRevision;


        @Override
        public Observable<ExecutionResult> doAction() {
            mUpdateUrl = String.format(PFServiceEndpoints.PATIENT_UPDATE_URL, mPatientSvc.getAuthToken().getEmail());
            mCurRevision = mPatientSvc.getPatientData().get_rev();
            return subscribeIoObserveImmediate(Observable.create(subscriber -> {
                Timber.d("Requesting url: %s", mUpdateUrl);
                try {
                    HttpsURLConnection connection = PFNetworkManager.getPFHttpsURLConnection(mUpdateUrl);
                    connection.setRequestProperty("Bearer", mPatientSvc.getAuthToken().toAuthTokenHeaderString());
                    connection.setRequestMethod("HEAD");
                    connection.addRequestProperty("Cache-Control", "no-cache");
                    String serverRev = connection.getHeaderField("Etag");
                    if (serverRev != null && mCurRevision != null) {
                        boolean updateReq = !mCurRevision.equalsIgnoreCase(serverRev);
                        if (updateReq) {
                            Timber.i("Update required. Cached patient document is: %s / Server has: %s", Arrays.asList(mCurRevision, serverRev));
                            mPatientSvc.setNeedsServerUpdate(true);
                        } else {
                            Timber.d("Update not required. Cached patient document is: %s / Server has: %s", Arrays.asList(mCurRevision, serverRev));
                            mPatientSvc.setNeedsServerUpdate(false);
                        }
                        subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                        subscriber.onCompleted();
                    } else if (mCurRevision == null) {
                        Timber.i("Current patient doc does not have a revision.");
                        mPatientSvc.setNeedsServerUpdate(true);
                        subscriber.onNext(ExecutionResult.RESULT_SUCCESS);
                        subscriber.onCompleted();
                    } else {
                        Timber.e("No ETag headers provided.");
                        for (String header : connection.getHeaderFields().keySet()) {
                            for (String value : connection.getHeaderFields().get(header)) {
                                Timber.e("Header: " + header + "/" + value);
                            }
                        }
                        subscriber.onError(new RuntimeException("No etag provided from server."));
                    }
                } catch (IOException e) {
                    Timber.e(e,"IO error checking for revision.");
                    subscriber.onError(e);
                }
            }));
        }
    }
}
