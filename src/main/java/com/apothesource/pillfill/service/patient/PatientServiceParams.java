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

import java.util.Arrays;
import java.util.List;

public class PatientServiceParams {
    public static final String BASE_URL = "https://developer.pillfill.com";
    public static final String RXP_BASE_URL = BASE_URL;
    public static final String RX_SERVICES_URL = "https://rxservices.pill-fill.com";
    private static final String BASE_PATIENT_URL = BASE_URL + "/service/patient/%s";
    public static final String PATIENT_UPDATE_URL = BASE_PATIENT_URL + "?doConsistencyCheck=false";

    public static final String REMINDER_BASE_URL = RX_SERVICES_URL + "/service/rx/%s/reminders";
    public static final String DEVICES_BASE_URL = RX_SERVICES_URL + "/service/devices?subjectName=%s";
    public static final String DEVICE_BASE_URL = RX_SERVICES_URL + "/service/device/%s";
    public static final String CODE_URL = BASE_URL + "/service/login/oauth/verify?code=%s";
    public static final String ANONYMOUS_TOKEN_URL = BASE_URL + "/service/anonymousAccount";
    public static final String PATIENT_UPDATE_PASSWORD_URL = PATIENT_UPDATE_URL + "&setUsername=true";
    //No patient context methods
    public static final String SPL_INFO_URL = RXP_BASE_URL + "/service/drugSplList?ids=%s";
    public static final String NDFRT_BY_UNII_URL = RXP_BASE_URL + "/service/drugNdfrtListByUNII?ids=%s";
    public static final String NDFRT_INFO_URL = RXP_BASE_URL + "/service/drugNdfrtList?ids=%s";
    public static final String NDFRT_SINGLE_INFO_URL = RXP_BASE_URL + "/service/drugs/drugNdfrtConcept?nui=%s";
    public static final String NDFRT_INGREDIENTS_URL = RXP_BASE_URL + "/service/drugNdfrtIngredientConcepts?%s";
    public static final String NDC_TO_NDFRT_URL = RXP_BASE_URL + "/service/drugs/drugNdfrtInformation?ndc=%s";
    public static final String NDC_TO_NUI_URL = RXP_BASE_URL + "/service/drugs/getNUIsFromNDC?ndc=%s";
    public static final String SPL_TO_NDFRT_URL = RXP_BASE_URL + "/service/drugs/drugNdfrtInformation?splId=%s";
    public static final String NDFRT_INTERACTION_URL = RXP_BASE_URL + "/service/interactions?%s";
    public static final String NDFRT_CONCEPT_URL = RXP_BASE_URL + "/service/drugs/getInheritedNdfrtConcept?nui=%s";
    public static final String PRESCRIBERS_URL = RXP_BASE_URL + "/service/prescriberList?ids=%s";
    public static final String PRESCRIBERS_RELATED_INFO = RXP_BASE_URL + "/service/prescribers/relatedInfoSearch?npi=%s";
    public static final String PRESCRIBERS_PHONE_SEARCH_URL = RXP_BASE_URL + "/service/prescribers/search?ph=%s";
    public static final String PRESCRIBER_RX_HISTORY_URL = RXP_BASE_URL + "/service/prescriberRxHistory?id=%s";
    public static final String PRESCRIBERS_STATE_NAME_SEARCH_URL = RXP_BASE_URL + "/service/prescribers/search?st=%s&lName=%s&fName=%s";
    public static final String PHARMACIES_URL = RXP_BASE_URL + "/service/pharmacyList?ids=%s";
    public static final String PHARMACIES_SEARCH_URL = RXP_BASE_URL + "/service/pharmacies/search?keyword=%s&lat=%f&lng=%f&radius=%d&pgtoken=%s";
    public static final String PHARMACY_GET_PLACE_URL = RXP_BASE_URL + "/service/pharmacy/googleplace?ref=%s";
    public static final String PRESCRIPTIONS_URL = RXP_BASE_URL + "/service/prescriptionList?ids=%s";
    public static final String RX_EXTRACT_URL = RXP_BASE_URL + "/service/processRxData";
    public static final String RX_ENRICH_URL = RXP_BASE_URL + "/service/enrichRxData";
    public static final String RX_STATS_URL = RXP_BASE_URL + "/service/statsData";
    public static final String RX_DEBUG_URL = RXP_BASE_URL + "/service/debugLog";
    public static final String DRUG_ALERT_URL = RXP_BASE_URL + "/service/drugAlertList?ids=%s";
    public static final String DRUG_FTI_SEARCH_URL = RXP_BASE_URL + "/service/drugs/search?product=%s&page=%d";
    public static final String DRUG_NDC_SEARCH_URL = RXP_BASE_URL + "/service/drugs/search?ndc=%s";
    public static final String CONCEPT_CONDITIONS_SEARCH_URL = RXP_BASE_URL + "/service/drugs/searchForConditions?name=%s";
    public static final String CONCEPT_SUBSTANCES_SEARCH_URL = RXP_BASE_URL + "/service/drugs/searchForSubstances?name=%s";
    public static final String DEFINITION_SERVICE_URL = RXP_BASE_URL + "/service/getDescriptions?%s";
    public static final String WORD_SERVICE_URL = RXP_BASE_URL + "/service/defineWord?%s";
    public static final String MRTD_SERVICE_URL = RXP_BASE_URL + "/service/mrtdLoadCalculations?ids=%s&weightInKgs=%d";
    public static final String MEDHISTORY_FAX_SUBMIT_URL = RXP_BASE_URL + "/service/medHistory/%s/faxRequests";
    public static final String MEDHISTORY_FAX_STATUS_URL = RXP_BASE_URL + "/service/medHistory/%s/faxRequest/%s";
    public static final String HELP_FAQ_URL = RXP_BASE_URL + "/preprod/fmcompare/android_faq.jsp";
    public static final String LOGIN_URL = BASE_URL + "/service/login";
    public static final String TWITTER_LOGIN_URL = BASE_URL + "/service/twitter_web_login";
    public static final String FACEBOOK_LOGIN_URL = BASE_URL + "/service/login/facebook";
    public static final String PWD_LOGIN_URL = BASE_URL + "/service/login/pfaccount?id=%s&pwdHash=%s";
    public static final String OAUTH_LOGIN_URL = BASE_URL + "/service/login/oauth";
    public static final String EXTRACTOR_CONFIGS_URL = BASE_URL + "/service/extractor/extractorConfigs";
    public static final String CR_RECOMMENDATIONS_BASE_URL = "https://developer.pillfill.com";
    public static final String CR_RECOMMENDATION_BY_NUI_URL = CR_RECOMMENDATIONS_BASE_URL + "/cr_drug_details.html?NUI=%s";
    public static final String CR_RECOMMENDATION_BY_CLASS_URL = CR_RECOMMENDATIONS_BASE_URL + "/cr_drug_details.html?drugClass=%s";
    public static final String CR_RECOMMENDATION_BROWSE_URL = CR_RECOMMENDATIONS_BASE_URL + "/cr_drug_details.html";
    public static final String CR_RECOMMENDATION_LOOKUP_NUI_URL = CR_RECOMMENDATIONS_BASE_URL + "/service/drugs/cr/drugInformation/nui/%s";
    public static final String CR_RECOMMENDATION_LOOKUP_UNII_URL = CR_RECOMMENDATIONS_BASE_URL + "/service/drugs/cr/drugInformation/unii/%s";
    //External links
    public static final String EXT_LICENSE_INFO_URL = "http://www.pill-fill.com/licenses.txt";
    //	public static final String RX_LIST_URL = BASE_URL + "/service/rxTypeList";
    public static final String EXT_TOS_INFO_URL = "http://www.pill-fill.com/privacy_policy.html";
    public static final String EXT_DRUG_INTERACTION_URL = "http://rxnav.nlm.nih.gov/REST/Ndfrt/interaction?nuis=%s&scope=3";
    public static final String EXT_SPL_DOWNLOAD_URL = "http://dailymed.nlm.nih.gov/dailymed/downloadzipfile.cfm?setId=%s";
    public static final String EXT_SPL_PDF_URL = "http://dailymed.nlm.nih.gov/dailymed/downloadpdffile.cfm?setid=%s";
    public static final String EXT_SPL_WEB_URL = "http://dailymed.nlm.nih.gov/dailymed/drugInfo.cfm?setid=%s";
    public static final String EXT_SPL_TO_RXNORM_URL = "http://rxnav.nlm.nih.gov/REST/rxcui?idtype=SPL_SET_ID&id=%s";
    public static final String EXT_NDC_TO_RXNORM_URL = "http://rxnav.nlm.nih.gov/REST/rxcui?idtype=NDC&id=%s";
    public static final String EXT_RXNORM_INTERACTIONS_URL = "http://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=%s";
    public static final String CERT_PUBLIC_KEY[] = new String[]{
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnwIeJvIAaMOAAP1foR438M5Q8rTGxdaY1SgbsFmmoxnTCt5BxNErAZFrb4e6XINTqPtxr06V+d8SSZ5ztA7lO//Bix8f6NXPP+EMxrZL/AJj5LGqeqh5vgbIaJNHYWAgAW8TLsdZ8tju8EyU6MbIah4sJpFSVhlw1KzM08NPXXdskkfNNfBG5VOxXjf3cGDD3rFHjyzoPhN43ZK+TOL2CP1Ug5YdCzzQzOVPODwlWPeV7omnRCiRq6N4Y1PxoxyvHyr/TeIDCgDY8a1hvEBb+MrCa4yDnYyTw3kJNfI2pyRwkydBUMTBRG4cwpqTupOOCbdWYyrSrTzJ4G1PB6L3twIDAQAB",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv0Ktz4F0ao/9F3faM09sAxPb9lqQFncWxN16rEzqTtaiQYpmzSeUgmt964YjBViwHALksLgrrN/skluo6Wm7goD9E/QntrQMVC+AcsQyMMpwFb3bxWgiR7Oc7xDqaIlczypFmbSKIFehitxIyxI6omrmHv2OX3DOKtwLHN+yI2itObMPXC9sE8ozkaa9uj+zGNnHKx2LPuB+ZbuIOXHgFl5Rhqp/qrTiLB7cSI91kVxe+5msO2MrcsgiGG1kCdXbZ79LCokqjqzX9otmDgij7HtF0tb2ufYEBziuJ9ip1oY30UcLkooUkxA/KwEASiR7v+qkd/fU3vXUj1SqRixrbQIDAQAB",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA07bDcilKU0pQbs/8TR0QasSFp85oJOY4BJImeNrqYkl4xtSe6JTdBpvJE7BdtDfa71d0JeOIW5dcX32Ynq8makl0d7KfMLDx7/A49fnBjK++vIBfQoWjuQsMoWejNW9PvPyzLIvJnFjik8dv1YlpjJNajcK3vXlHJLOpMjaX17Me5XopFULiTrw3cKhYEg38REyNrOp1/gLGOrQseXPTXtoK3Ow0Sa6yHubw3VUEhP3BlxZ5CpeNmGTkKR+L1IjngNPEQ0Zhqdo+ZOJURHbjzN39peXfRgQFut/ODg5oDv+RQPOUxDeh7C8UJDu5uBqjJGfdb78Av6ApwiFAPlGY7wIDAQAB"
    };
    //Patient-specific services
    public static final String CHECK_USERNAME_URL = BASE_PATIENT_URL + "/checkAvailable?username=%s";
    public static final String PATIENT_LINK_ACCOUNT_URL = BASE_PATIENT_URL + "/link?authToken=%s&account=%s";
    public static String DRUG_UPC_NDC_URL = RXP_BASE_URL + "/service/drugs/upc/%s/drugSplInformation";
    public static String DRUG_UPC_URL = RXP_BASE_URL + "/service/drugs/search?upc=%s";
    public static String DRUG_PRICE_CHECK_URL = RXP_BASE_URL + "/service/drugNdfrtGenericDrugPricing?ids=%s";
    public static String DRUG_IMG_URL = BASE_URL + "/service/drugs/ndc/%s/drugImage.jpg";
    public static List<String> filterImageUrls = Arrays
            .asList("http://www.cvs.com/webcontent/images/prescription/default-drug-image.png", "http://www.cvs.com/webcontent/images/drug/default-drug-image.png");
    public static String[] scopes = new String[]{
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/userinfo.profile"};

}
