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
package com.apothesource.pillfill.service.prescription.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import rx.Subscription;

import static org.junit.Assert.assertTrue;

/**
 * Created by Michael Ramirez on 5/29/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultPrescriptionServiceImplTest {
    private static final Logger log = Logger.getAnonymousLogger();
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    @Test
    public void testLoadPrescriptions() throws Exception {
        String[] rxIds = new String[]{
                "RX-00090D9ABEAFFDE010EAD13E8E845AF2232976A745C710F8801EC8F35BCD7494",
                "RX-00153170CDEC5571789987505799C59EB63C06BCC821B23E3D70A566F3F95A70",
                "RX-002C0ABB528855B112BD9E50282B19CD7A01A4E95657C3090BA6F6042BC89208"
        };

        ArrayList<PrescriptionType> rxList = new ArrayList<>();
        DefaultPrescriptionServiceImpl impl = new DefaultPrescriptionServiceImpl();
        Subscription subscription = impl.getPrescriptions(Arrays.asList(rxIds)).subscribe(rx -> {
            log.info(gson.toJson(rx));
            rxList.add(rx);
        }, throwable -> {
            throw new RuntimeException("Error retrieving rxs from server", throwable);
        }, () -> {
            assertTrue(rxList.size() == 3);
        });

        while (!subscription.isUnsubscribed()) {
            Thread.sleep(1000);
        }
    }
}