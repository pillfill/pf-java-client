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
package com.apothesource.pillfill.service.reminder;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.Reminder;
import com.apothesource.pillfill.datamodel.Reminder.ReminderWSResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by Michael Ramirez on 6/3/15. Copyright 2015, Apothesource, Inc. All Rights Reserved.
 */
public interface ReminderService {
    String REMINDER_SERVICE_BASE_PATH = "/service/rx/";
    String REMINDER_SERVICE_TEMP_PATH = "%s/reminders";

    /**
     * Update the reminders for the provided prescription. This method will replace any
     * existing reminders. 
     * 
     * Limitations:
     * - A maximum of 4 reminders are allowed per prescription
     * - The prescription must be active (computedInactiveDate is after the current date)
     * 
     * @param reminderList The list of reminders to apply to the reminder
     * @param rx The prescription to update
     * @return The server's response
     */
    Observable<ReminderWSResponse> updateRemindersForRx(List<Reminder> reminderList, PrescriptionType rx);

    /**
     * Retrieve reminders for the provided prescription
     * 
     * @param rx The prescription in question
     * @return A stream of reminders applied to the prescription
     */
    Observable<Reminder> getRemindersForRx(PrescriptionType rx);

}
