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

import java.util.ArrayList;

/**
 * Created by rammic on 12/23/14.
 */
public class SplMediaData {
    public static final String SPL_METADATA_URL = "http://dailymed.nlm.nih.gov/dailymed/services/v2/spls/%s/media.json";

    public Data data;
    public Metadata metadata;

    public static class Metadata {
        public String elements_per_page, next_page_url, total_pages, total_elements, current_url, next_page, previous_page, previous_page_url, current_page;
    }

    public static class Data {
        public String spl_version, setid, title;
        public ArrayList<Media> media = new ArrayList<>();
    }

    public static class Media {
        public String mime_type, name, url;
    }

}
/**
 * {
 * "metadata": {
 * "elements_per_page": "100",
 * "next_page_url": "null",
 * "total_pages": "1",
 * "total_elements": "3",
 * "current_url": "http:\/\/dailymed.nlm.nih.gov\/dailymed\/services\/v2\/spls\/1efe378e-fee1-4ae9-8ea5-0fe2265fe2d8\/media.json",
 * "next_page": "null",
 * "previous_page": "null",
 * "previous_page_url": "null",
 * "current_page": "1"
 * },
 * "data": {
 * "spl_version": "1",
 * "media": [{
 * "mime_type": "image\/jpeg",
 * "name": "edecrin-01.jpg",
 * "url": "http:\/\/dailymed.nlm.nih.gov\/dailymed\/image.cfm?setid=1efe378e-fee1-4ae9-8ea5-0fe2265fe2d8&name=edecrin-01.jpg"
 * }, {
 * "mime_type": "image\/jpeg",
 * "name": "edecrin-02.jpg",
 * "url": "http:\/\/dailymed.nlm.nih.gov\/dailymed\/image.cfm?setid=1efe378e-fee1-4ae9-8ea5-0fe2265fe2d8&name=edecrin-02.jpg"
 * }, {
 * "mime_type": "image\/jpeg",
 * "name": "edecrin-03.jpg",
 * "url": "http:\/\/dailymed.nlm.nih.gov\/dailymed\/image.cfm?setid=1efe378e-fee1-4ae9-8ea5-0fe2265fe2d8&name=edecrin-03.jpg"
 * }],
 * "published_date": "Mar 12, 2012",
 * "setid": "1efe378e-fee1-4ae9-8ea5-0fe2265fe2d8",
 * "title": "EDECRIN (ETHACRYNIC ACID) TABLET [ATON PHARMA, INC.]"
 * }
 * }
 */

