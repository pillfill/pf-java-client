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
package com.apothesource.pillfill.datamodel.android;

import java.io.Serializable;
import java.util.List;

public class GooglePlaceSearchResult implements Serializable {
    public String icon, id, name, reference, vicinity;
    public float price_level, rating;
    public List<String> html_attributions;
    public Geometry geometry;
    public List<Photo> photos;

    public static class Geometry {
        public Location location;
    }

    public static class Location {
        public float lat, lng;
    }

    public static class PlaceSearchResponse {
        public String next_page_token;
        public String status;
        public List<GooglePlaceSearchResult> results;
    }

    public class Photo {
        public String photo_reference;
        public int height, width;
        public List<String> types;
    }

    // {
    // "geometry": {
    // "location": {
    // "lat": 34.828876,
    // "lng": -82.549317
    // }
    // },
    // "icon":
    // "http://maps.gstatic.com/mapfiles/place_api/icons/shopping-71.png",
    // "id": "a31a1bc151a540c6d1020591ba8aaa7f980ab0c7",
    // "name": "Walmart Supercenter",
    // "opening_hours": {
    // "open_now": true
    // },
    // "photos": [{
    // "height": 864,
    // "html_attributions": ["From a Google User"],
    // "photo_reference":
    // "CnRwAAAAflglrfG_Zz2VOtr9Poc4mID28RR7ZbPCBZBfFVrlP7Tym2V6UGaiwM-GlIJ729B0xumodlipMhNjjHRhjsKIRltrj7xxs3Jqt0NPWjnZd0jFlee7c0DonuYPf7LG1sTNewVriLMAn6k93Y2mY4B8fBIQvtQ0p15CuYyrcaV9WLp3oBoUPDKZvKft-xY-VZKreSz5s5-lfpw",
    // "width": 1296
    // }],
    // "price_level": 1,
    // "rating": 3.1,
    // "reference":
    // "CoQBdQAAABVe9e3JpjKlbjp2hiNoLn3UQwMMcrBNpPPxAwMlwEVrlgEl6qTCpSGWWjHaGvlEg1nRPQa2M2Z2n4xLYfK-QSVSyDBbkF8Sr5gC0T186cIltEP1TdILXvUDlhSiG1OMpktwO-ZfdO14qil9uKrM3gKELv8IQ-1ocAGQqzutxlC-EhBsterPNVGm8iciFAn9xBCLGhRVon4kJFvpbU1yhMRZ_XB1LmOdpQ",
    // "types": ["pharmacy", "department_store", "store", "health",
    // "establishment"],
    // "vicinity": "115 Rolling Hills Cir, Easley"
    // }

}
