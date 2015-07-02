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

import com.apothesource.pillfill.datamodel.android.GooglePlaceSearchResult;

import java.io.Serializable;

public class PharmacyType implements Serializable, Comparable<PharmacyType> {

    private static final long serialVersionUID = -1L;
    public transient GooglePlaceSearchResult searchResult;
    private String storeID;
    private String storeAddress;
    private String weekdayHours;
    private String saturdayHours;
    private String sundayHours;
    private String latitude;
    private String longitude;
    private String phoneNumber;
    private String uuid;
    private String _id, _rev;

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getStoreID() {
        return this.storeID;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getStoreAddress() {
        return this.storeAddress;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getWeekdayHours() {
        return this.weekdayHours;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setWeekdayHours(String weekdayHours) {
        this.weekdayHours = weekdayHours;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSaturdayHours() {
        return this.saturdayHours;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSaturdayHours(String saturdayHours) {
        this.saturdayHours = saturdayHours;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSundayHours() {
        return this.sundayHours;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSundayHours(String sundayHours) {
        this.sundayHours = sundayHours;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int compareTo(PharmacyType another) {
        if (this.getStoreID() == null) return -1;
        return this.getStoreID().compareTo(another.getStoreID());
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
