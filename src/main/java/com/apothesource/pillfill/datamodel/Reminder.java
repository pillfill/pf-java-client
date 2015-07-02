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
package com.apothesource.pillfill.datamodel;

import com.apothesource.pillfill.exception.ReminderConfigurationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
/*
 * Copyright 2015, Apothesource Inc.
 * All Rights Reserved.
 * @author Michael Ramirez (michael@pillfill.com)
 */

/**
 * @author Michael Ramirez (michael@pillfill.com)
 */
public class Reminder {
    private static final String CRON_TEMPLATE = "%d %d %d * * %s";


    public String rxId;
    public String reminderId;
    public Set<String> deviceUuids = new HashSet<>();
    public Set<UserDevice> deviceList = new HashSet<>();
    public ReminderState state = ReminderState.CREATED;
    public ReminderType type = ReminderType.MED_REMINDER;
    public Integer fireHour;
    public Integer fireMinute;
    public Integer fireDays;
    public String subjectName;
    public String lang;
    public Reminder fallbackReminder;
    public String fallbackReminderId;
    public Integer waitTimeMinutes;
    public boolean debug = false;
    public long creationDate;
    public long lastFireDate;
    public String timeZone;
    public int utcMinOffset;
    public Set<String> mgrUuids = new HashSet<>();
    public String _rev;

    public static ArrayList<Reminder> updateAndValidateReminder(Reminder rm, PrescriptionType rx, int maxDepth) throws ReminderConfigurationException {
        return validateReminder(rm, rx, null, 0, maxDepth);
    }

    /**
     * Function to validate and link reminders into a flattened list
     *
     * @param rm         The reminder to be schedule
     * @param rx         The parent prescription for the reminder
     * @param reminderId A reminder ID for this reminder. May be null.
     * @param depth      The current depth of the fallback reminder. Used to ensure that reminders are not nested too deeply.
     * @param maxDepth   The maximum nesting level for fallback reminders
     * @return A list of reminders including this reminder and all child reminders (fallback)
     */
    public static ArrayList<Reminder> validateReminder(Reminder rm, PrescriptionType rx, String reminderId, int depth, int maxDepth) throws ReminderConfigurationException {
        ArrayList<Reminder> reminderList = new ArrayList<>();
        rm.state = ReminderState.ACTIVE;
        rm.rxId = rx.getUuid();
        rm.creationDate = new Date().getTime();
        rm.rxId = rx.getUuid();
        rm.reminderId = (reminderId != null) ? reminderId : UUID.randomUUID().toString();


        if ((rm.deviceList == null || rm.deviceList.isEmpty()) && rm.deviceUuids.isEmpty())
            throw new ReminderConfigurationException("Reminders must have at least one device associated with it.");
        if (rx.getComputedInactiveAfterDate().before(new Date()))
            throw new ReminderConfigurationException("Associated prescription ID is already inactive.");
        if (rm.type != ReminderType.MED_REMINDER && rm.fallbackReminder != null)
            throw new ReminderConfigurationException("Only MED_REMINDER types can have fallbacks.");


        if (depth == 0) {
            if (rm.fireHour == null || rm.fireMinute == null)
                throw new ReminderConfigurationException("Primary reminders must have a fire time defined.");
            rm.waitTimeMinutes = null;
            if (rm.fireDays == null) rm.fireDays = FireDay.EVERYDAY;
        } else if (depth < maxDepth) {
            rm.fireHour = null;
            rm.fireMinute = null;
            rm.fireDays = null;
            if (rm.waitTimeMinutes == null) rm.waitTimeMinutes = 15;
        } else {
            throw new ReminderConfigurationException(String.format("You can only have %d failback reminders.", maxDepth));
        }
        //We've validated this reminder, let's check failbacks
        reminderList.add(rm);

        if (rm.fallbackReminder != null) {
            Reminder failbackReminder = rm.fallbackReminder;
            rm.fallbackReminder = null;
            String failbackId = UUID.randomUUID().toString();
            rm.fallbackReminderId = failbackId;
            reminderList.addAll(validateReminder(failbackReminder, rx, failbackId, ++depth, maxDepth));
        }
        return reminderList;
    }

    public String toCronString() {
        return toCronString(!debug);
    }

    protected String toCronString(boolean randomSecond) {
        int randomSecondTrigger = randomSecond ? (int) (Math.random() * 60) : 0;
        return String.format(CRON_TEMPLATE, randomSecondTrigger, fireMinute, fireHour, FireDay.getFireDaysCronString(fireDays));
    }

    public boolean isPrimaryReminder() {
        return fireHour != null && fireMinute != null;
    }

    public enum ReminderState {
        CREATED, ACTIVE, INACTIVE, DELETED, INVALID
    }

    public enum ReminderType {
        MED_REMINDER, MED_REFILL_DUE, MED_QUANTITY_LOW, MISSED_REMINDER, MED_REMINDER_DEACTIVATED
    }

    public enum FireDay {
        SUN(0b0000001), MON(0b0000010), TUE(0b0000100), WED(0b0001000), THU(0b0010000), FRI(0b0100000), SAT(0b1000000);

        public static int EVERYDAY = 127;
        int day = 0;

        FireDay(int day) {
            this.day = day;
        }

        static public String getFireDaysCronString(int days) {
            if (days == EVERYDAY) {
                return "?";
            } else {
                return Arrays.asList(FireDay.values()).stream()
                        .filter(fireDay -> (fireDay.day & days) > 0) //See if this day bit is set
                        .map((FireDay fireDay) -> fireDay.name()) //If so, convert day to a string
                        .collect(Collectors.joining(",")); //Then concat them into a single string list
            }
        }
    }

    public static class ReminderWSResponse {
        public List<Reminder> reminders;
        public String error;
    }
//0=Sun
//1=Mon
//2=Tue
//3=Wed
//4=Thu
//5=Fri
//6=Sat
}