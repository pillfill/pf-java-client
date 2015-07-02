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
import java.util.List;

public class InteractionType implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<String> ndc;

    private String severity;

    private String uuid;


    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getNdc() {
        return this.ndc;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setNdc(List<String> ndc) {
        this.ndc = ndc;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSeverity() {
        return this.severity;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSeverity(String severity) {
        this.severity = severity;
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

}
