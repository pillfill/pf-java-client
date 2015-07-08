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

import com.apothesource.pillfill.datamodel.PatientType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.android.AuthToken;
import com.apothesource.pillfill.exception.PFMasterPasswordException;
import com.apothesource.pillfill.exception.PFBadTokenException;
import com.apothesource.pillfill.service.patient.command.ExecutionResult;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.ModificationType;
import com.apothesource.pillfill.service.patient.command.ModifyPatientActions.ModifyRxListAction;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Michael Ramirez on 5/29/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultPatientServiceImplTest {

    private static final Logger log = Logger.getAnonymousLogger();
    private static final Gson gson = new Gson();
    private DefaultPatientServiceImpl mPatientService;
    private AuthToken mAuthToken;
    private AuthToken mBadAuthToken;

    @Before
    public void initTest() {
        mPatientService = new DefaultPatientServiceImpl();
        mAuthToken = AuthToken.parse("{\"email\": \"ZPF@f05a5377-2c92-4492-a802-775cbeab2b6b\",\"agent\": \"ZPF@f05a5377-2c92-4492-a802-775cbeab2b6b\",\"expires\": \"05292016041023EDT\",\"issued\": \"05292015041023EDT\",\"sig\": \"1O/f4qznlmkTNF4tqcxW4Ee/bVX42DU61Ef84ctT8AA=\"}");
        mBadAuthToken = new AuthToken("test", new Date(), new Date());
        mBadAuthToken.setSignature("BAD_SIG");
        mBadAuthToken.setRawToken("BAD_TOKEN");
    }

    @Test
    public void testInit() throws Exception {
        mPatientService.init(mAuthToken, "testPassword").subscribe(patientType -> {
            assertNotNull(patientType);
            assertTrue(patientType.get_id() != null);
            log.fine(gson.toJson(patientType));
            mPatientService.getPatient().subscribe(ptDoc -> {
                log.fine(ptDoc.get_rev());
                assertNotNull(ptDoc.get_rev());
                assertEquals(ptDoc.get_id(), mAuthToken.getEmail());
            });
        });
    }

    @Test
    public void testBadAuthToken() throws Exception {
        try {
            PatientType badPt = mPatientService.init(mBadAuthToken, "testPassword").toBlocking().first();
            assertNull(badPt);
        }catch(PFBadTokenException e){
            log.fine("Caught expected bad token exception.");
        }
    }

    @Test
    public void testBadPassword() throws Exception {
        try {
            mPatientService.init(mAuthToken, "testBadPassword").toBlocking().last();
            fail("Did not throw exception on bad password.");
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof PFMasterPasswordException);
        }

    }

    @Test
    public void syncPatientWithServer() throws Exception {
        PatientType patientType = mPatientService.init(mAuthToken, "testPassword").toBlocking().first();

        assertNotNull(patientType);
        assertTrue(patientType.get_id() != null);
        String currentRev = patientType.get_rev();
        assertNotNull(currentRev);
        if(mPatientService.commitToServer().toBlocking().first() == ExecutionResult.RESULT_SUCCESS) {
            String newRev = patientType.get_rev();
            assertNotNull(newRev);
            assertNotEquals(currentRev, newRev);
            log.log(Level.FINE, "Patient doc updated to revision: %s", newRev);
        }else{
            fail("Failed to commit document to server.");
        }
    }

    public PrescriptionType generatePrescription() {
        PrescriptionType rx = new PrescriptionType();
        rx.setUuid(UUID.randomUUID().toString());
        rx.setMedicationName("Test Medication");
        rx.setDispenseDate(new Date());
        return rx;
    }

    @Test
    public void addAndRemoveRx() throws Exception {
        List<PrescriptionType> rxList = Arrays.asList(generatePrescription(), generatePrescription());
        ModifyRxListAction addAction = new ModifyRxListAction()
                .modificationType(ModificationType.CHANGE_ADD)
                .patientService(mPatientService)
                .prescriptions(rxList);
        PatientType pt = mPatientService.init(mAuthToken, "testPassword").toBlocking().first();
        assertNotNull(pt);
        assertTrue(pt.get_id() != null);
        if (mPatientService.doAction(addAction).toBlocking().last() == ExecutionResult.RESULT_SUCCESS) {
            List<PrescriptionType> activeRxs = mPatientService.getActivePrescriptions().toList().toBlocking().first();
            assert (activeRxs.size() == 2);
            List<PrescriptionType> inactiveRxs = mPatientService.getInactivePrescriptions().toList().toBlocking().first();
            assert (inactiveRxs.isEmpty());
        } else {
            throw new RuntimeException("Failed to add prescriptions.");
        }

        if (mPatientService.undoAction(addAction).toBlocking().last() == ExecutionResult.RESULT_SUCCESS) {
            List<PrescriptionType> activeRxs = mPatientService.getActivePrescriptions().toList().toBlocking().first();
            assert (activeRxs.isEmpty());
            List<PrescriptionType> inactiveRxs = mPatientService.getInactivePrescriptions().toList().toBlocking().first();
            assert (inactiveRxs.isEmpty());
        } else {
            throw new RuntimeException("Failed to remove prescriptions.");
        }
    }
}