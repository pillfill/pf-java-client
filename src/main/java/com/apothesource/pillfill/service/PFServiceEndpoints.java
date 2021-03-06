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
package com.apothesource.pillfill.service;

public class PFServiceEndpoints {
    public static final String DEVELOPER_BASE_URL = "https://developer.pillfill.com";
    public static final String RX_SERVICES_URL = "https://rxservices.pill-fill.com";

    //Patient-specific services
    public static final String BASE_PATIENT_URL = DEVELOPER_BASE_URL + "/service/v1/patients/%s"; //Base URL to retrieve a patient document
    public static final String CHECK_USERNAME_URL = BASE_PATIENT_URL + "/checkAvailable?username=%s"; //Check if a username is available
    public static final String PATIENT_UPDATE_URL = BASE_PATIENT_URL + "?doConsistencyCheck=false"; //Update a patient and skip the monotonicity check
    public static final String CODE_URL = DEVELOPER_BASE_URL + "/service/v1/patients?code=%s"; //For Linking Account to Social Media (e.g. Google)


    //Auth Token Services
    public static final String PWD_LOGIN_URL = DEVELOPER_BASE_URL + "/service/v1/authTokens/%s?pwdHash=%s"; //Get an authToken for an existing account using a username/password
    public static final String ANONYMOUS_TOKEN_URL = DEVELOPER_BASE_URL + "/service/v1/authTokens/anonymous"; //Url to generate a new AuthToken (i.e. create a new account)

    //Reminder Services
    public static final String REMINDER_BASE_URL = RX_SERVICES_URL + "/service/rx/%s/reminders";
    public static final String DEVICES_BASE_URL = RX_SERVICES_URL + "/service/devices?subjectName=%s";
    public static final String DEVICE_BASE_URL = RX_SERVICES_URL + "/service/device/%s";

    //Drug Information Services
    public static final String DRUG_INFO_URL = DEVELOPER_BASE_URL + "/service/v1/medications/%s";
    public static final String DRUG_ALERT_URL = DEVELOPER_BASE_URL + "/service/v1/drug/%s/alerts";
    public static final String MRTD_ALERT_URL = DEVELOPER_BASE_URL + "/service/v1/interactions/mrtd?ids=%s&weightInKgs=%d";

    //Pharmacy Services
    public static final String PHARMACY_URL = DEVELOPER_BASE_URL + "/service/v1/pharmacies/%s";
    public static final String PHARMACY_SEARCH_URL = DEVELOPER_BASE_URL + "/service/v1/pharmacies?keyword=%s&lat=%f&lng=%f&radius=%d&pgtoken=%s";
    public static final String PHARMACY_GET_PLACE_URL = DEVELOPER_BASE_URL + "/service/pharmacies/googleplace?ref=%s";

    //Prescriber Services
    public static final String PRESCRIBER_URL = DEVELOPER_BASE_URL + "/service/v1/prescribers/%s";
    public static final String PRESCRIBER_PHONE_SEARCH_URL = DEVELOPER_BASE_URL + "/service/v1/prescribers/search?ph=%s";
    public static final String PRESCRIBER_RX_HISTORY_URL = DEVELOPER_BASE_URL + "/service/v1/prescribers/%s/rx_history";
    public static final String PRESCRIBER_STATE_NAME_SEARCH_URL = DEVELOPER_BASE_URL + "/service/v1/prescribers?st=%s&lName=%s&fName=%s";

    //Prescription Services
    public static final String PRESCRIPTIONS_URL = DEVELOPER_BASE_URL + "/service/v1/prescriptions/%s";
    public static final String RX_ENRICH_URL = DEVELOPER_BASE_URL + "/service/v1/tasks/enrichmentTasks";
    public static final String RX_REQUEST_EXTRACT_URL = DEVELOPER_BASE_URL + "/service/v1/tasks/accountAggregationTasks";
    public static final String RX_EXTRACT_STATUS_URL = DEVELOPER_BASE_URL + "/service/v1/tasks/accountAggregationTasks/%s";


    //NIH Drug Information Services
    public static final String EXT_RXNORM_INTERACTIONS_URL = "http://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=%s";

}
