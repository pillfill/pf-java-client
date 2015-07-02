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
import com.apothesource.pillfill.service.cache.impl.DefaultNoOpCacheServiceImpl;
import com.apothesource.pillfill.service.device.DeviceManagerService;
import com.apothesource.pillfill.service.device.impl.DefaultDeviceManagerServiceImpl;
import com.apothesource.pillfill.service.drug.DrugAlertService;
import com.apothesource.pillfill.service.drug.DrugService;
import com.apothesource.pillfill.service.drug.impl.DefaultDrugServiceImpl;
import com.apothesource.pillfill.service.drug.impl.DefaultDrugAlertServiceImpl;
import com.apothesource.pillfill.service.patient.PatientServiceLocator;
import com.apothesource.pillfill.service.patient.impl.DefaultPatientServiceLocatorImpl;
import com.apothesource.pillfill.service.pharmacy.PharmacyService;
import com.apothesource.pillfill.service.pharmacy.impl.DefaultPharmacyServiceImpl;
import com.apothesource.pillfill.service.prescriber.PrescriberService;
import com.apothesource.pillfill.service.prescriber.impl.DefaultPrescriberServiceImpl;
import com.apothesource.pillfill.service.prescription.PrescriptionService;
import com.apothesource.pillfill.service.prescription.impl.DefaultPrescriptionServiceImpl;
import com.apothesource.pillfill.service.reminder.ReminderService;
import com.apothesource.pillfill.service.reminder.impl.DefaultReminderServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Ramirez on 6/10/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
@Module
public class DefaultServiceModule {
    @Provides @Singleton PatientServiceLocator getPatientServiceLocator(){
        return new DefaultPatientServiceLocatorImpl();
    }

    @Provides @Singleton DrugService getDrugDataService(){
        return new DefaultDrugServiceImpl();
    }

    @Provides @Singleton DrugAlertService getDrugAlertService(){
        return new DefaultDrugAlertServiceImpl();
    }

    @Provides @Singleton DeviceManagerService getDeviceManagerService(){
        return new DefaultDeviceManagerServiceImpl();
    }

    @Provides @Singleton ReminderService getReminderService(){
        return new DefaultReminderServiceImpl();
    }

    @Provides @Singleton PrescriptionService getPrescriptionService(){
        return new DefaultPrescriptionServiceImpl();
    }

    @Provides @Singleton PharmacyService getPharmacyService(){
        return new DefaultPharmacyServiceImpl();
    }
    @Provides @Singleton PrescriberService getPrescriberService(){
        return new DefaultPrescriberServiceImpl();
    }
    @Provides @Singleton CacheService getCacheService(){
        return new DefaultNoOpCacheServiceImpl();
    }
}
