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
package com.apothesource.pillfill.service.reminder.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.google.gson.Gson;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.fail;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public class DefaultReminderServiceImplTest {
    private static Logger log = Logger.getAnonymousLogger();
    private static Gson gson = new Gson();
    private int reminderCount = 0;

    @Test
    public void testGetRemindersForRx() throws Exception {
        DefaultReminderServiceImpl impl = new DefaultReminderServiceImpl();
        PrescriptionType rx = new PrescriptionType();
        rx.setUuid("F70D9BD0-9969-4B52-A129-64EC9713F912");
        impl.getRemindersForRx(rx).subscribe(reminder -> {
            log.info(gson.toJson(reminder));
            reminderCount++;
        }, throwable -> {
            log.severe(throwable.toString());
            throwable.printStackTrace();
            fail();
        }, () -> {
            assert (reminderCount == 1);
        });

    }
}