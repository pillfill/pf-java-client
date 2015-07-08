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
package com.apothesource.pillfill.service.drug;

import com.apothesource.pillfill.datamodel.DrugInformation;
import com.apothesource.pillfill.datamodel.NdfSearchResultEntry;
import com.apothesource.pillfill.datamodel.SplSearchResultEntry;
import com.apothesource.pillfill.datamodel.android.GenericDrugPriceMapping;
import com.apothesource.pillfill.datamodel.ndfrt.Concept;
import com.apothesource.pillfill.datamodel.ndfrt.FullConcept;
import com.apothesource.pillfill.datamodel.spl.SplEntry;
import com.apothesource.pillfill.datamodel.spl.SplInformation;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Michael Ramirez on 6/15/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 * 
 * Wrapper for server-side drug translation and related functions. 
 * 
 * You must set the <code>pfApiKey</code> System Property to use this class. You can get an API
 * key for <a href="https://developer.pillfill.com">PillFill at the Developer Website.</a>.
 * 
 */
public interface DrugService {
    /**
     * GSON TokenType parameters. 
     */
    Type SPL_LIST_TYPE = new TypeToken<ArrayList<SplInformation>>() {}.getType();
    Type NDFRT_LIST_TYPE = new TypeToken<ArrayList<FullConcept>>() {}.getType();
    Type DRUGINFO_LIST_TYPE = new TypeToken<ArrayList<DrugInformation>>() {}.getType();
    Type STRING_LIST_TYPE = new TypeToken<ArrayList<String>>() {}.getType();
    Type NDFRT_GENERIC_PRICE_TYPE = new TypeToken<ArrayList<GenericDrugPriceMapping>>() {}.getType();
    Type NDFRT_CONCEPT_LIST_TYPE = new TypeToken<ArrayList<Concept>>() {}.getType();
    Type SEARCH_RESULT_LIST_TYPE = new TypeToken<ArrayList<SplSearchResultEntry>>() {}.getType();
    Type SEARCH_CONCEPT_LIST_TYPE = new TypeToken<ArrayList<NdfSearchResultEntry>>() {}.getType();
    String INDICATIONS_AND_USAGE_SECTION = "34067-9";

    /**
     * Calls the PF server to translate a single, 11-digit NDC to a RxNorm Id.
     * @param ndc The <a href="">NDC</a> to translate (cannot be null)
     * @return An Observable String containing the RxNorm ID. OnComplete will be called directly if none are found.
     */
    Observable<String> getRxNormIdForDrugNdc(String ndc);

    /**
     * Calls the PF server to translate a set of <a href="http://www.fda.gov/ForIndustry/DataStandards/SubstanceRegistrationSystem-UniqueIngredientIdentifierUNII/">UNII</a>s to NDF Concepts.
     * @param uniis The list of ingredient IDs
     * @return A stream of concepts associated with the provided UNIIs. 
     */
    Observable<FullConcept> getNdfrtConceptsByUnii(String... uniis);

    /**
     * Calls the PF server to translate a set of <a href="http://www.fda.gov/ForIndustry/DataStandards/StructuredProductLabeling/default.htm">SplIds</a> to detailed <code>SPLInformation</code> objects.
     * @param splIdList A list of SplIds to retrieve
     * @return A stream of SplInformation objects associated with the splIds.
     */
    Observable<SplEntry> getSplInformation(String... splIdList);

    /**
     * Calls to the PF server to retrieve a set of <a href="http://www.nlm.nih.gov/research/umls/sourcereleasedocs/current/VANDF/">National Drug File</a> concepts by concept ID(NUI).
     * @param nuis The list of NUIs to retrieve
     * @return A stream of {@link FullConcept} concepts
     */
    Observable<FullConcept> getNdfrtInformation(String... nuis);

    /**
     * Calls to the PF server to retrieve a set of Brand Names associated with a specific RxNorm ID.
     * @param rxnormId The rxnormId to retrieve
     * @return A stream of Strings for possible brand names for this concept
     */
    Observable<String> getBrandNamesForDrug(String rxnormId);

    Observable<DrugInformation> getDrugInformation(String... ids);
}
