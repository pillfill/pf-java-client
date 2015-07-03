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
package com.apothesource.pillfill.service.drug.impl;

/**
 * Created by Michael Ramirez on 6/1/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */

import com.apothesource.pillfill.datamodel.DrugAlertType;
import com.apothesource.pillfill.datamodel.MRTDCalculation;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.rxnorm.interaction.FullInteractionType;
import com.apothesource.pillfill.datamodel.rxnorm.interaction.FullInteractionTypeGroup;
import com.apothesource.pillfill.datamodel.rxnorm.interaction.InteractionConcept;
import com.apothesource.pillfill.datamodel.rxnorm.interaction.InteractionPair;
import com.apothesource.pillfill.datamodel.rxnorm.interaction.MinConceptItem;
import com.apothesource.pillfill.network.PFNetworkManager;
import com.apothesource.pillfill.service.cache.CacheService;
import com.apothesource.pillfill.service.cache.impl.DefaultNoOpCacheServiceImpl;
import com.apothesource.pillfill.service.drug.DrugAlertService;
import com.apothesource.pillfill.service.PFServiceEndpoints;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;
import com.google.common.base.Joiner;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import rx.Observable;

public class DefaultDrugAlertServiceImpl implements DrugAlertService {
    public static final Type ALERT_LIST_TYPE = new TypeToken<ArrayList<DrugAlertType>>() {
    }.getType();
    public static final Type MRTD_LIST_TYPE = new TypeToken<ArrayList<MRTDCalculation>>() {
    }.getType();
    private static final float MRTD_OVER_LOAD_THRESHOLD = 100;
    private static final float MRTD_HIGH_LOAD_THRESHOLD = 90;
    private static final Gson gson = new Gson();

    private static final Logger log = Logger.getLogger("DrugAlertSvcImpl");
    private final OkHttpClient externalHttpClient;
    private CacheService cache;


    public DefaultDrugAlertServiceImpl() {
        externalHttpClient = new OkHttpClient();
        cache = new DefaultNoOpCacheServiceImpl();
    }
    
    private List<DrugAlertType> getDrugAlertsFromMrtdCalculations(Collection<MRTDCalculation> mrtds, float weightInKgs) {
        ArrayList<DrugAlertType> retList = new ArrayList<>();

        for (MRTDCalculation mrtd : mrtds) {
            DrugAlertType dat = new DrugAlertType();
            dat.setType("MRTD");
            dat.setKey("MRTD:" + mrtd.unii + ":" + mrtd.currentLoad);
            if (mrtd.relatedRxs != null) {
                dat.setRelatedRxIds(mrtd.relatedRxs);
            }
            if (mrtd.currentLoad < MRTD_OVER_LOAD_THRESHOLD && mrtd.currentLoad > MRTD_HIGH_LOAD_THRESHOLD) {
                dat.setTitle(ResourceBundle.getBundle("mrtd-messages").getString("highDrugDoseTitle"));
                dat.setCritical(false);
                dat.setReason(Collections.singletonList(String.format(ResourceBundle.getBundle("mrtd-messages").getString("highDrugDoseMessage"), (int)weightInKgs)));
                retList.add(dat);
            } else if (mrtd.currentLoad > MRTD_OVER_LOAD_THRESHOLD) {
                dat.setKey("MRTD:" + mrtd.unii + ":" + mrtd.currentLoad);
                dat.setTitle(ResourceBundle.getBundle("mrtd-messages").getString("potentialOverdoseTitle"));
                dat.setCritical(true);
                dat.setReason(Collections.singletonList(String.format(ResourceBundle.getBundle("mrtd-messages").getString("potentialOverdoseMessage"), (int)weightInKgs)));
                retList.add(dat);
            }
            dat.setResolution(Collections.singletonList(ResourceBundle.getBundle("mrtd-messages").getString("resolutionBody")));
        }
        return retList;
    }

    private String getMrtdUrlString(Collection<PrescriptionType> activeRxList, float weightInKgs) {
        return String.format(
                PFServiceEndpoints.MRTD_ALERT_URL,
                Joiner.on("&ids=").join(
                        Observable.from(activeRxList)
                                .map(PrescriptionType::getUuid)
                                .toBlocking()
                                .toIterable()), 
                ((int) weightInKgs));
    }

    @Override
    public Observable<DrugAlertType> checkForAllAlerts(Collection<PrescriptionType> activeRxList, float weightInKgs) {
        return subscribeIoObserveImmediate(Observable.merge(
                Arrays.asList(
                        checkForDrugDoseWarnings(activeRxList, weightInKgs),
                        checkForDrugAlerts(activeRxList),
                        checkForRxNormInteractions(activeRxList)
                )
        ));
        
    }

    @Override
    public Observable<DrugAlertType> checkForDrugDoseWarnings(Collection<PrescriptionType> activeRxList, final float weightInKgs) {
        return subscribeIoObserveImmediate(Observable.create(subscriber -> {
            if (!activeRxList.isEmpty()) {
                final String urlStr = getMrtdUrlString(activeRxList, weightInKgs);
                Optional<String> cachedResponse = cache.getCachedData(urlStr);
                if (cachedResponse.isPresent()) {
                    try {
                        List<MRTDCalculation> mrtds = gson
                                .fromJson(cachedResponse.get(), MRTD_LIST_TYPE);
                        Observable.from(getDrugAlertsFromMrtdCalculations(mrtds, weightInKgs)).forEach(subscriber::onNext);
                        subscriber.onCompleted();
                        return;
                    } catch (Exception e) {
                        log.warning("Error handling cached MRTD response. Failing back to server.");
                    }
                }

                log.fine("Checking for drug alerts to URL: " + urlStr);
                try {
                    OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
                    Request req = new Request.Builder().url(urlStr).build();
                    log.fine("Requesting MRTD calculations from server: " + urlStr);
                    
                    String response = client.newCall(req).execute().body().string();
                    cache.setCachedData(urlStr, response);
                    List<MRTDCalculation> mrtds = gson.fromJson(response, MRTD_LIST_TYPE);
                    Observable.from(getDrugAlertsFromMrtdCalculations(mrtds, weightInKgs)).forEach(subscriber::onNext);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    log.log(Level.WARNING, "Could not retrieve alerts.", e);
                    subscriber.onError(e);
                }
            } else {
                subscriber.onCompleted();
            }
        }));
    }

    @Override
    public Observable<DrugAlertType> checkForDrugAlerts(Collection<PrescriptionType> rxList) {
        ArrayList<String> ndcList = new ArrayList<>();
        for (PrescriptionType rx : rxList) {
            if (rx.getNdc() != null) ndcList.add(rx.getNdc());
        }

        final String urlParam = Joiner.on("&ids=").join(rxList);
        return subscribeIoObserveImmediate(Observable.create(subscriber -> {
            try {
                if (ndcList.isEmpty()) {
                    subscriber.onCompleted();
                } else {
                    String url = String.format(
                            PFServiceEndpoints.DRUG_ALERT_URL, urlParam);
                    log.fine(String.format("Checking for drug alerts to URL: %s", url));
                    OkHttpClient client = PFNetworkManager.getPinnedPFHttpClient();
                    Request req = new Request.Builder().url(url).build();
                    
                    String response = client.newCall(req).execute().body().string();
                    List<DrugAlertType> alerts = gson.fromJson(response, ALERT_LIST_TYPE);

                    Observable.from(alerts).map(alert -> {
                        alert.setKey(alert.getAdditionalInfoUrl());
                        return alert;
                    }).forEach(subscriber::onNext);

                    subscriber.onCompleted();
                }
            } catch (IOException e) {
                log.log(Level.WARNING, "Could not retrieve alerts.", e);
                subscriber.onError(e);
            }
        }));
    }


    @Override
    public Observable<DrugAlertType> checkForRxNormInteractions(Collection<PrescriptionType> rxList) {
        HashSet<String> cuiSet = new HashSet<>();
        Observable<String> rxNormIdList = Observable.from(rxList).filter(rx -> rx.getRxNormId() != null).map(PrescriptionType::getRxNormId);
        cuiSet.addAll(rxNormIdList.toList().toBlocking().first());

        return subscribeIoObserveImmediate(Observable.create(subscriber -> {
            if (cuiSet.size() < 2) {
                log.fine("Not processing drug interaction set size of " + cuiSet.size());
                subscriber.onCompleted();
            } else {
                // Check to see if this set has been queried before
                ArrayList<String> cuiList = new ArrayList<>(cuiSet);
                Collections.sort(cuiList);
                String param = Joiner.on("+").join(cuiList);
                final String url = String.format(
                        PFServiceEndpoints.EXT_RXNORM_INTERACTIONS_URL, param);

                Optional<String> cachedResult = cache.getCachedData(url);
                if (cachedResult.isPresent()) {
                    log.fine("Returning cached result for URL query: " + url);
                    List<DrugAlertType> alertList = deserializeDrugAlerts(rxList, cachedResult.get());
                    Observable.from(alertList).forEach(subscriber::onNext);
                    subscriber.onCompleted();
                } else {
                    log.fine("Requesting Interaction URL: " + url);
                    Request.Builder rxNormInteractionCheckRequest = new Request.Builder();
                    Request req = rxNormInteractionCheckRequest.url(url).build();
                    try {
                        Response responseObject = externalHttpClient.newCall(req).execute();
                        String responseMsg = responseObject.body().string();
                        cache.setCachedData(url, responseMsg);
                        List<DrugAlertType> drugAlerts = deserializeDrugAlerts(rxList, responseMsg);
                        Observable.from(drugAlerts).forEach(subscriber::onNext);
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        log.log(Level.WARNING, "Error processing drug interaction response.", e);
                        subscriber.onError(new RuntimeException(e));
                    }
                }
            }
        }));
    }

    private List<DrugAlertType> deserializeDrugAlerts(Collection<PrescriptionType> rxs, String msg) {
        try {
            JsonObject returnValue = new JsonParser().parse(msg).getAsJsonObject();
            if (!returnValue.has("fullInteractionTypeGroup")) {
                return new ArrayList();
            } else {
                Gson gson = new Gson();
                TypeToken<List<FullInteractionTypeGroup>> interactionGroupListType = new TypeToken<List<FullInteractionTypeGroup>>() {
                };
                List<FullInteractionTypeGroup> group = gson.fromJson(returnValue.get("fullInteractionTypeGroup"), interactionGroupListType.getType());
                ArrayList<DrugAlertType> alerts = processDrugInteractions(rxs, group);
                return alerts;

            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Invalid drug interaction alert response.", e);
            throw new RuntimeException(e);
        }
    }

    public CacheService getCache() {
        return cache;
    }

    public void setCache(CacheService cache) {
        this.cache = cache;
    }

    private ArrayList<DrugAlertType> processDrugInteractions(Collection<PrescriptionType> rxs,
                                                             Collection<FullInteractionTypeGroup> interactions) {
        if (interactions == null || interactions.isEmpty())
            return new ArrayList<>();
        ArrayList<DrugAlertType> alerts = new ArrayList<DrugAlertType>(
                interactions.size());
        log.fine("Found " + interactions.size() + " interactions.");
        for (FullInteractionTypeGroup group : interactions) {
            for (FullInteractionType interaction : group.getFullInteractionType()) {
                DrugAlertType alert = new DrugAlertType();

                InteractionPair pair = interaction.getInteractionPair().get(0);
                alert.setType("Drug Interaction");
                InteractionConcept drug0 = pair.getInteractionConcept().get(0);
                InteractionConcept drug1 = pair.getInteractionConcept().get(1);
                alert.setTitle("Potential Drug Interaction");
                String severity = pair.getSeverity();
                alert.setResolution(Collections.singletonList("Please check with your doctor or pharmacist to make sure this is intentional."));
                if (severity.equalsIgnoreCase("CRITICAL"))
                    alert.setCritical(true);
                alert.setAdditionalInfoUrl(drug0.getSourceConceptItem().getUrl());
                alert.setReason(Collections.singletonList(pair.getDescription()));
                alert.setKey("DRUG_INTERACTION+" + drug0.getMinConceptItem().getName() + "+" + drug1.getMinConceptItem().getName());

                for (PrescriptionType rx : rxs) {
                    String rxNormId = rx.getRxNormId();
                    if (rxNormId != null) {
                        for (MinConceptItem mc : interaction.getMinConcept()) {
                            if (rxNormId.equalsIgnoreCase(mc.getRxcui())) {
                                alert.getRelatedRxIds().add(rx.getUuid());
                            }
                        }
                    }
                }
                alerts.add(alert);
            }
        }

        return alerts;
    }


}
