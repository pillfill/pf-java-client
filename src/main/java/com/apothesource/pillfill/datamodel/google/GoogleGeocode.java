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

package com.apothesource.pillfill.datamodel.google;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleGeocode {
    public static class AddressComponent {

        private String longName;
        private String shortName;
        private List<String> types = new ArrayList<String>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The longName
         */
        public String getLongName() {
            return longName;
        }

        /**
         * @param longName The long_name
         */
        public void setLongName(String longName) {
            this.longName = longName;
        }

        /**
         * @return The shortName
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * @param shortName The short_name
         */
        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        /**
         * @return The types
         */
        public List<String> getTypes() {
            return types;
        }

        /**
         * @param types The types
         */
        public void setTypes(List<String> types) {
            this.types = types;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public static class GeocodeResponse {

        private List<Result> results = new ArrayList<Result>();
        private String status;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The results
         */
        public List<Result> getResults() {
            return results;
        }

        /**
         * @param results The results
         */
        public void setResults(List<Result> results) {
            this.results = results;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public static class Geometry {

        private Location location;
        private String locationType;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The location
         */
        public Location getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         * @return The locationType
         */
        public String getLocationType() {
            return locationType;
        }

        /**
         * @param locationType The location_type
         */
        public void setLocationType(String locationType) {
            this.locationType = locationType;
        }


        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public static class Location {

        private float lat;
        private float lng;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The lat
         */
        public float getLat() {
            return lat;
        }

        /**
         * @param lat The lat
         */
        public void setLat(float lat) {
            this.lat = lat;
        }

        /**
         * @return The lng
         */
        public float getLng() {
            return lng;
        }

        /**
         * @param lng The lng
         */
        public void setLng(float lng) {
            this.lng = lng;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public static class Result {

        private List<AddressComponent> addressComponents = new ArrayList<AddressComponent>();
        private String formattedAddress;
        private Geometry geometry;
        private String placeId;
        private List<String> postcodeLocalities = new ArrayList<String>();
        private List<String> types = new ArrayList<String>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         * @return The addressComponents
         */
        public List<AddressComponent> getAddressComponents() {
            return addressComponents;
        }

        /**
         * @param addressComponents The address_components
         */
        public void setAddressComponents(List<AddressComponent> addressComponents) {
            this.addressComponents = addressComponents;
        }

        /**
         * @return The formattedAddress
         */
        public String getFormattedAddress() {
            return formattedAddress;
        }

        /**
         * @param formattedAddress The formatted_address
         */
        public void setFormattedAddress(String formattedAddress) {
            this.formattedAddress = formattedAddress;
        }

        /**
         * @return The geometry
         */
        public Geometry getGeometry() {
            return geometry;
        }

        /**
         * @param geometry The geometry
         */
        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        /**
         * @return The placeId
         */
        public String getPlaceId() {
            return placeId;
        }

        /**
         * @param placeId The place_id
         */
        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        /**
         * @return The postcodeLocalities
         */
        public List<String> getPostcodeLocalities() {
            return postcodeLocalities;
        }

        /**
         * @param postcodeLocalities The postcode_localities
         */
        public void setPostcodeLocalities(List<String> postcodeLocalities) {
            this.postcodeLocalities = postcodeLocalities;
        }

        /**
         * @return The types
         */
        public List<String> getTypes() {
            return types;
        }

        /**
         * @param types The types
         */
        public void setTypes(List<String> types) {
            this.types = types;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
