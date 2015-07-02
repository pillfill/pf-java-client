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
package com.apothesource.pillfill.datamodel.spl;

import java.io.Serializable;
import java.util.List;

public class SplInformation implements Serializable {

    private static final long serialVersionUID = -1L;

    private SplEntry splEntry;

    private String id;

    private String rev;

    private List<NdfrtConcept> ndfrtConcept;


    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.spl.SplEntry
     */
    public SplEntry getSplEntry() {
        return this.splEntry;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.spl.SplEntry
     */
    public void setSplEntry(SplEntry splEntry) {
        this.splEntry = splEntry;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getId() {
        return this.id;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRev() {
        return this.rev;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRev(String rev) {
        this.rev = rev;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.spl.NdfrtConcept>
     */
    public List<NdfrtConcept> getNdfrtConcept() {
        return this.ndfrtConcept;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.spl.NdfrtConcept>
     */
    public void setNdfrtConcept(List<NdfrtConcept> ndfrtConcept) {
        this.ndfrtConcept = ndfrtConcept;
    }

}
