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
package com.apothesource.pillfill.service.drug.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.SplSearchResultEntry;
import com.apothesource.pillfill.datamodel.ndfrt.Concept;
import com.apothesource.pillfill.datamodel.ndfrt.FullConcept;
import com.apothesource.pillfill.datamodel.ndfrt.Property;
import com.apothesource.pillfill.datamodel.ndfrt.Role;
import com.apothesource.pillfill.datamodel.spl.Ingredient;
import com.apothesource.pillfill.datamodel.spl.Pkg;
import com.apothesource.pillfill.datamodel.spl.Product;
import com.apothesource.pillfill.datamodel.spl.SplEntry;
import com.apothesource.pillfill.datamodel.spl.SplInformation;
import com.apothesource.pillfill.network.PillFillSSLSocketFactory;
import com.apothesource.pillfill.service.PFBaseServiceContext;
import com.apothesource.pillfill.service.drug.DrugService;
import com.apothesource.pillfill.service.patient.PatientServiceParams;
import com.apothesource.pillfill.utilites.ResourceUtil;
import static com.apothesource.pillfill.utilites.ReactiveUtils.subscribeIoObserveImmediate;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import rx.Observable;
import timber.log.Timber;

public class DefaultDrugServiceImpl extends PFBaseServiceContext implements DrugService {
    private static final String URL_RXNORM_BRAND_NAME_BY_NDC = "http://rxnav.nlm.nih.gov/REST/rxcui.json?idtype=NDC&id=%s";
    private static Gson gson = new GsonBuilder().disableHtmlEscaping().setDateFormat("yyyy-MM-dd").create();
    private final XPathExpression XPATH_GET_DRUG_BN;
    private final Properties urlToTypeMapping;
    private final String apiKey;

    public Comparator<SplSearchResultEntry> scoreComparator = (lhs, rhs) -> {
        try {
            float lhsScore = Float.parseFloat(lhs.getScore());
            float rhsScore = Float.parseFloat(rhs.getScore());
            if (lhsScore == rhsScore) return 0;
            return (lhsScore - rhsScore) < 0 ? -1 : 1;
        } catch (Exception e) {
            return 0;
        }
    };
    
    public DefaultDrugServiceImpl() {
        XPath path = XPathFactory.newInstance().newXPath();
        urlToTypeMapping = ResourceUtil.getInstance().getMappingResources();
        apiKey = ResourceUtil.getInstance().getPFApiKey();
        try {
            XPATH_GET_DRUG_BN = path.compile("//conceptGroup[tty[text()='SBD']]/conceptProperties/synonym/text()");
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Could not compile BrandName xpath", e);
        }
        
    }

    /**
     *
     *
     *
     *
     *
     *
     *
     *
     * Observable Network Call-Dependent Methods
     *
     *
     *
     *
     *
     *
     *
     *
     */

    /**
     * <a href="http://rxnav.nlm.nih.gov/RxNormAPIs.html#">NIH Service</a>: Get avaliable RxNorm IDs associated with an 11 digit National Drug Code (NDC).
     *
     * @param ndc The 11-digit NDC without dashes or spaces
     * @return A list of RxNorm IDs associated with this NDC (should normally return 0 or 1)
     */
    @Override
    public Observable<String> getRxNormIdForDrugNdc(String ndc) {
        return subscribeIoObserveImmediate(subscriber -> {
            String urlStr = String.format(URL_RXNORM_BRAND_NAME_BY_NDC, ndc);
            try{
                String responseStr = PillFillSSLSocketFactory.doPinnedGetForUrl(urlStr);
                JsonParser parser = new JsonParser();
                JsonObject response = parser.parse(responseStr).getAsJsonObject().get("idGroup").getAsJsonObject();
                if (response.has("rxnormId")) {
                    JsonArray array = response.getAsJsonArray("rxnormId");
                    for(JsonElement e : array){
                        subscriber.onNext(e.getAsString());
                    }
                    subscriber.onCompleted();
                } else {
                    Timber.e("No rxnormIds found for NDC: %s", ndc);
                    subscriber.onError(new RuntimeException("Could not find NDC->RxNorm for NDC."));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    /**
     * Retrieve {@link FullConcept} for for the provided FDA <code><a href="http://www.fda.gov/ForIndustry/DataStandards/SubstanceRegistrationSystem-UniqueIngredientIdentifierUNII/">UNique Ingredient Identifier</a></code>.
     * @param uniis A list of the Drug UNIIs to be retrived
     * @return An observable that emits a {@link FullConcept} for each related concept found in the National Drug File (NDF)
     */
    @Override
    public Observable<FullConcept> getNdfrtConceptsByUnii(String... uniis) {
        if (uniis == null || uniis.length == 0) return Observable.empty();
        String uniiList = Joiner.on("&ids=").join(uniis);
        final String url = String.format(PatientServiceParams.NDFRT_BY_UNII_URL, uniiList);
        return subscribeIoObserveImmediate(subscriber -> {
            try{
                String response = PillFillSSLSocketFactory.doPinnedGetForUrl(url);
                List<FullConcept> concepts = gson.fromJson(response, NDFRT_LIST_TYPE);
                Observable.from(concepts).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Couldn't get NDFRT concepts for UNIIs: %s", uniis.toString());
                subscriber.onError(e);
            }
        });
    }

    /**
     * Retrieve {@link SplInformation} for the provided SPL (FDA's Standard Packaging Label) Drug IDs. Each {@link SplInformation} instance represents a specific product & package on the US Drug Market (both OTC & RX). The class includes basic patient-focused drug information, ingredients, strengths, etc.
     * @param splIdList The list of SPL IDs to lookup
     * @return An observable that emits a {@link SplInformation} instance for each drug
     * @see SplEntry
     */
    @Override
    public Observable<SplInformation> getSplInformation(String... splIdList) {
        if (splIdList == null || splIdList.length == 0) return Observable.empty();

        String splIdListString = Joiner.on("&ids=").join(splIdList);
        String url = String.format(PatientServiceParams.SPL_INFO_URL,
                splIdListString);
        Timber.d("Requesting SPL URL: %s");
        return subscribeIoObserveImmediate(subscriber -> {
           try{
               String response = PillFillSSLSocketFactory.doPinnedGetForUrl(url);
               List<SplInformation> list = gson.fromJson(response, SPL_LIST_TYPE);
               Observable.from(list).forEach(subscriber::onNext);
               subscriber.onCompleted();
           } catch (IOException e) {
               Timber.e("Error getting spl information for %s - %s", splIdList, e.getMessage());
               subscriber.onError(e);
           }
        });

    }

    /**
     * Retrieve {@link FullConcept} for for the provided National Drug File (NDF) Concept IDs (NUIs). IDs should be in standard NDF Concept ID format of 'N' followed by 10 digits (i.e. (N[\d]{10}) - e.g. 'N0000155155')
     * @param nuis A list of the NUIs to be retrived
     * @return An observable that emits {@link FullConcept}s based on the provided NUI list
     */
    @Override
    public Observable<FullConcept> getNdfrtInformation(String... nuis) {
        if(nuis == null || nuis.length == 0) return Observable.empty();

        String params = Joiner.on("&ids=").join(nuis);
        String url = String
                .format(PatientServiceParams.NDFRT_INFO_URL, params);
        Timber.d("Requesting NDFRT URL: %s", url);
        return subscribeIoObserveImmediate(subscriber -> {
            try{
                String response = PillFillSSLSocketFactory.doPinnedGetForUrl(url);
                List<FullConcept> concepts = gson.fromJson(response, NDFRT_LIST_TYPE);
                Observable.from(concepts).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Error getting NDFRT with NUI: %s - %s", nuis, e.getMessage());
                subscriber.onError(e);
            }
        });
    }

    /**
     * An experimental method to enable a list of generic concepts to be retrieved without a method-specific implementation. Use with caution.
     * @param type The {@link Type} of class to be returned. Must be mapped in the <code>/src/main/resources/PFResourceMapping.properties</code> file or a RuntimeException will occur.
     * @param ids The list of IDs to retrieve
     * @return An observable that emits instances of the provided type based on the requested IDs
     */
    protected <T> Observable<T> getConceptList(Class<T> type, String... ids) {
        if(ids == null || ids.length == 0) return Observable.empty();
        String urlTemplate = getUrlForType(type);
        String url = String.format(urlTemplate, Joiner.on(",").join(ids));
        String typeName = type.getSimpleName();
        Timber.d("Requesting %s list from URL: %s", typeName, url);
        return subscribeIoObserveImmediate(subscriber -> {
            try{
                String listJson = PillFillSSLSocketFactory.doPinnedGetForUrl(url);
                JsonParser parser = new JsonParser();
                JsonArray array = parser.parse(listJson).getAsJsonArray();
                for(JsonElement elem : array){
                    subscriber.onNext(gson.fromJson(elem, type));
                }
                subscriber.onCompleted();
            } catch (IOException e) {
                Timber.e("Error retrieving %s list with ids %s - %s", typeName, ids, e.getMessage());
                subscriber.onError(e);
            }
        });
    }

    private String getUrlForType(Class t){
        String[] components = t.getSimpleName().split("[\\.]");
        String simpleName = components[components.length - 1].replaceAll("[^\\w]","");
        return urlToTypeMapping.getProperty(simpleName);
    }
    /**
     * Get a list of brand names associated with the provided drug identified by its RxNorm ID (CUI).
     * @param rxnormId The RxNormID for the generic drug
     * @return A list of brand names associated with the generic drug
     */
    @Override
    public Observable<String> getBrandNamesForDrug(String rxnormId) {
        if(rxnormId == null) return Observable.empty();

        return subscribeIoObserveImmediate(subscriber -> {
            try {
                List<String> brandNames = loadBrandNameDrug(rxnormId);
                Observable.from(brandNames).forEach(subscriber::onNext);
                subscriber.onCompleted();
            } catch (Exception e) {
                Timber.e(e, "Error accessing brand name for rxNormId: %s", rxnormId);
                subscriber.onError(e);
            }
        });
    }

    private List<String> loadBrandNameDrug(final String rxnormId) throws IOException {
        final String rxNormUrlTemplate = "http://rxnav.nlm.nih.gov/REST/rxcui/%s/allrelated";
        final String urlString = String.format(rxNormUrlTemplate, rxnormId);
        String response = PillFillSSLSocketFactory.doPinnedGetForUrl(urlString);
        try {
            NodeList nl = (NodeList) XPATH_GET_DRUG_BN.evaluate(new InputSource(new StringReader(response)), XPathConstants.NODESET);
            ArrayList<String> retList = new ArrayList<>();
            for (int i = 0; i < nl.getLength(); i++) {
                retList.add(nl.item(i).getNodeValue());
            }
            return retList;
        }catch(XPathExpressionException e){
            Timber.e(e, "Error processing response: %s", response);
        }
        return Collections.emptyList();
    }

    /**
     *
     *
     *
     *
     *
     *
     * Static Utility Methods.
     *
     *
     *
     *
     *
     *
     **/

    public static Optional<Product> getProductByNdc(SplEntry entry, String ndc) {
        for (Product p : entry.getProducts().getProduct()) {
            for (Pkg pkg : p.getPkg()) {
                if (ndc != null && pkg.getNdc() != null && pkg.getNdc().startsWith(ndc)) return Optional.of(p);
            }
        }
        return Optional.absent();
    }

    public static ArrayList<Concept> getIngredients(FullConcept fc) {
        return getRolesWithName("has_Ingredient", fc);
    }

    public static ArrayList<Concept> getTreatedConditions(FullConcept fc) {
        return getRolesWithName("may_treat", fc);
    }

    public static ArrayList<String> getUnii(FullConcept fc) {
        return getPropertiesWithName("FDA_UNII", fc);
    }

    public static ArrayList<String> getProperty(String propertyName, FullConcept fc) {
        return getPropertiesWithName(propertyName, fc);
    }

    public static String getDefinition(FullConcept fc) {
        ArrayList<String> list = getPropertiesWithName("MeSH_Definition", fc);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public static ArrayList<Concept> getContraindications(FullConcept fc) {
        return getRolesWithName("CI_with", fc);
    }

    private static ArrayList<Concept> getRolesWithName(String name, FullConcept fc) {
        ArrayList<Concept> retList = new ArrayList<>();
        for (Role role : fc.getGroupRoles().getRole()) {
            if (role.getRoleName().equalsIgnoreCase(name)) {
                retList.add(role.getConcept());
            }
        }
        return retList;
    }

    private static ArrayList<String> getPropertiesWithName(String name, FullConcept fc) {
        ArrayList<String> retList = new ArrayList<>();
        if (fc == null) return retList;
        if (fc.getGroupProperties() == null) return retList;
        if (fc.getGroupProperties().getProperty() == null) return retList;
        for (Property property : fc.getGroupProperties().getProperty()) {
            if (property.getPropertyName().equalsIgnoreCase(name)) {
                retList.add(property.getPropertyValue());
            }
        }
        return retList;
    }

    public static List<String> getUniisFromSpl(SplEntry splEntry) {
        HashSet<String> uniis = new HashSet<>();
        if (splEntry == null || splEntry.getProducts() == null || splEntry.getProducts().getProduct() == null)
            return null;
        for (Product p : splEntry.getProducts().getProduct()) {
            if (p.getIngredient() == null) {
                return null;
            }
            for (Ingredient i : p.getIngredient()) {
                uniis.add(i.getUnii());
            }
        }
        return new ArrayList<>(uniis);
    }

    public static String padWithZeros(String src, int size) {
        if (src.length() >= size) {
            return src;
        } else {
            return padWithZeros("0" + src, size);
        }
    }
    public static String getNdc(SplEntry entry) {
        ArrayList<String> ndcs = getNdcs(entry);
        if (ndcs.isEmpty()) return null;
        return ndcs.get(0);
    }

    public static ArrayList<String> getNdcs(SplEntry entry) {
        ArrayList<String> ndcs = new ArrayList<>();
        if (entry.getProducts() != null && entry.getProducts().getProduct() != null && !entry.getProducts().getProduct().isEmpty()) {
            for (Product p : entry.getProducts().getProduct()) {
                if (p.getPkg() != null && !p.getPkg().isEmpty()) {
                    for (Pkg pkg : p.getPkg()) {
                        if (pkg.getNdc() != null) ndcs.add(pkg.getNdc());
                    }
                }
            }
        }
        return ndcs;
    }

    public static List<String> getIdsForPrescriptions(ArrayList<PrescriptionType> rxList) {
        ArrayList<String> retList = new ArrayList<>();
        for (PrescriptionType rx : rxList) {
            retList.add(rx.get_id());
        }
        return retList;
    }

}
