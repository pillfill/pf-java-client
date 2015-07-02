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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PrescriptionReminder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static class CalendarDetails implements Serializable {
        public long calendarId;
        public String displayName, accountName, ownerName;
    }

    public static class CalendarReminderTime implements Serializable {
        public boolean deleted = false;
        public long eventId;
        public Date reminderTime;

        public CalendarReminderTime(long id, Date d) {
            eventId = id;
            reminderTime = d;
        }
    }

    public static class CalendarReminderEvent implements Serializable {
        private static final long serialVersionUID = 1L;
        public CalendarDetails parentCalendar;
        public String nui;
        public transient boolean isNew = false;
        public String patientId;
        public String revision;
        public int color_id;
        public boolean isPrivate;
        public String reminderTitle, reminderNote, reminderLocation;
        public int frequency;
        public Date beginDate, endDate;
        public ArrayList<CalendarReminderTime> reminders = new ArrayList<CalendarReminderTime>();

        public boolean isSetOnDay(int day) {
            return (frequency & (1 << day)) > 0;
        }

        public void setOnDay(int day, boolean isSet) {
            if (isSetOnDay(day) == isSet) return;

            if (isSet) {
                frequency = frequency + (1 << day);
            } else {
                frequency = frequency - (1 << day);
            }
        }
    }

    public static class ColorDetails {
        public int id, color;

        public ColorDetails(int id, int color) {
            this.id = id;
            this.color = color;
        }
    }

}
