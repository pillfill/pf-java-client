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

import com.apothesource.pillfill.service.cache.CacheService;
import com.apothesource.pillfill.service.drug.DrugAlertService;
import com.apothesource.pillfill.service.drug.DrugService;
import com.apothesource.pillfill.service.patient.PatientServiceLocator;
import com.apothesource.pillfill.service.pharmacy.PharmacyService;
import com.apothesource.pillfill.service.prescriber.PrescriberService;
import com.apothesource.pillfill.service.prescription.PrescriptionService;

import javax.inject.Inject;

/**
 * Created by Michael Ramirez on 6/15/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public abstract class PFBaseServiceContext {
    @Inject protected DrugService defaultDrugServiceImpl;
    @Inject protected DrugAlertService drugAlertService;
    @Inject protected CacheService cacheService;
    @Inject protected PatientServiceLocator patientServiceLocator;
    @Inject protected PharmacyService pharmacyService;
    @Inject protected PrescriberService prescriberService;
    @Inject protected PrescriptionService prescriptionService;
}
