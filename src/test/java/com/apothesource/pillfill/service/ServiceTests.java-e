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
package com.apothesource.pillfill.service;

import com.apothesource.pillfill.service.device.impl.DefaultDeviceManagerServiceImplTest;
import com.apothesource.pillfill.service.drug.impl.DefaultDrugServiceImplTest;
import com.apothesource.pillfill.service.patient.impl.DefaultPatientServiceImplTest;
import com.apothesource.pillfill.service.patient.impl.LoadDataActionsTests;
import com.apothesource.pillfill.service.prescription.impl.DefaultPrescriptionServiceImplTest;
import com.apothesource.pillfill.service.reminder.impl.DefaultReminderServiceImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import timber.log.Timber;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
@Suite.SuiteClasses({
        PFServiceContextTest.class,
        DefaultDeviceManagerServiceImplTest.class,
        DefaultPatientServiceImplTest.class,
        DefaultPrescriptionServiceImplTest.class,
        DefaultReminderServiceImplTest.class,
        LoadDataActionsTests.class,
        DefaultDrugServiceImplTest.class
})
@RunWith(ParallelSuite.class)
public class ServiceTests {
        public ServiceTests(){
                Timber.plant(new Timber.DebugTree());
        }
}
