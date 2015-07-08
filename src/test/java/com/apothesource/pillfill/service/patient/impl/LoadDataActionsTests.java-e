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
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.android.SecurePatientTypeWrapper;
import com.apothesource.pillfill.service.drug.impl.DefaultDrugAlertServiceImpl;
import com.apothesource.pillfill.service.patient.command.ExecutionResult;
import com.apothesource.pillfill.service.patient.impl.DefaultPatientServiceImpl.LoadRxDataAction;
import com.apothesource.pillfill.service.patient.impl.DefaultPatientServiceImpl.UpdateDrugAlertsAction;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Michael Ramirez on 6/4/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class LoadDataActionsTests {

    static Logger log = Logger.getAnonymousLogger();
    List<String> goodRxIds = Arrays.asList(
            "00153170CDEC5571789987505799C59EB63C06BCC821B23E3D70A566F3F95A70",
            "00322EE66A9600EB26EEFB60486AC91D088047A2AAE0FC72A5B98F2A532EE6B6",
            "0062C0AABE42724321B9FE3AD7A5533232A067AFF7FCC040EE0AA676AB524594",
            "00B762A2E8D722EE26049585501E0E42F40206574716F0EC90FCF486E75607FE"
    );
    List<String> badRxIds = Arrays.asList(
            "XX153170CDEC5571789987505799C59EB63C06BCC821B23E3D70A566F3F95A70",
            "XX322EE66A9600EB26EEFB60486AC91D088047A2AAE0FC72A5B98F2A532EE6B6",
            "XX62C0AABE42724321B9FE3AD7A5533232A067AFF7FCC040EE0AA676AB524594",
            "XXB762A2E8D722EE26049585501E0E42F40206574716F0EC90FCF486E75607FE"
    );

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testLoadGoodRxIdsAction() throws Exception {
        LoadRxDataAction action = new LoadRxDataAction().prescriptionIds(goodRxIds);
        PatientServiceImpl patientService = new DefaultPatientServiceImpl();
        action.patientService(patientService);
        if (action.doAction().toBlocking().last() == ExecutionResult.RESULT_SUCCESS) {
            log.info("Load completed.");
            List<PrescriptionType> rxList = patientService.getAllPrescriptions().toList().toBlocking().first();
            assertThat("All good RxIds load.", rxList.size(), is(goodRxIds.size()));
        } else {
            fail("Failed to load good RxIds.");
        }
    }

    @Test
    @Ignore("Service fails with 404 when bad IDs are included. Fix needs to occur on server.")
    public void testLoadBadRxIdsAction() throws Exception {
        LoadRxDataAction action = new LoadRxDataAction().prescriptionIds(badRxIds);
        PatientServiceImpl patientService = new DefaultPatientServiceImpl();
        action.patientService(patientService);
        if (action.doAction().toBlocking().last() == ExecutionResult.RESULT_SUCCESS) {
            log.info("Load completed.");
            List<PrescriptionType> rxList = patientService.getAllPrescriptions().toList().toBlocking().first();
            assertThat("Bad RxIds don't load.", rxList.size(), is(0));
        } else {
            fail("Error loading bad rxIds.");
        }
    }

    @Test
    public void testCheckForInteractions() throws Exception {
        DefaultPatientServiceImpl patientService = new DefaultPatientServiceImpl();
        SecurePatientTypeWrapper pt = new SecurePatientTypeWrapper();
        pt.getUserData().setWeightInKgs(80f);
        patientService.setPatient(pt);

        LoadRxDataAction loadRxAction = new LoadRxDataAction()
                .prescriptionIds(
                        "519AE8C6D5BBDE06202F5218CBD98E086D14FF290D733B0CCB6CD3EDF64B07B8",
                        "38D403CE09423680BA315B19A18666E71D82F2D62D434C5C5906C68454D48BC2"
                ).patientService(patientService);

        ExecutionResult result = loadRxAction.doAction().toBlocking().first();
        if (result == ExecutionResult.RESULT_SUCCESS) {
            List<PrescriptionType> rxList = patientService.getAllPrescriptions().toList().toBlocking().first();
            assertThat("2 prescriptions loaded correctly", rxList.size(), is(2));
            UpdateDrugAlertsAction action = new UpdateDrugAlertsAction()
                    .patientService(patientService)
                    .setPrescriptions(rxList)
                    .drugAlertService(new DefaultDrugAlertServiceImpl());

            if (action.doAction().toBlocking().last() == ExecutionResult.RESULT_SUCCESS) {
                ArrayList<DrugAlertType> alertList = new ArrayList<>();
                for (PrescriptionType rx : rxList) {
                    Set<DrugAlertType> drugAlerts = rx.getDrugAlerts();
                    alertList.addAll(drugAlerts);
                    for (DrugAlertType alert : drugAlerts) {
                        log.info("Drug Interaction: " + alert.getType());
                    }
                }
                assertThat("3 interactions loaded.", alertList.size(), is(3));
            } else {
                fail("Failed to process interactions.");
            }
        } else {
            fail("Failed to load prescriptions.");
        }

    }
}