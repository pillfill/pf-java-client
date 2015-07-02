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

import java.util.ArrayList;

public class GenericDrugPriceMapping {

    protected String _id, _rev;
    private ArrayList<String> retailNames = new ArrayList<String>();
    private String rxNormId;
    private String nui;
    private String ndfrtName;
    private String drugName;

    public GenericDrugPriceMapping(String drugName) {
        this.drugName = drugName;
    }

    /**
     * @return the retailNames
     */
    public ArrayList<String> getRetailNames() {
        return retailNames;
    }

    /**
     * @param retailNames the retailNames to set
     */
    public void addRetailName(String retailName) {
        if (!retailNames.contains(retailName)) {
            retailNames.add(retailName);
        }
    }

    /**
     * @return the rxNormId
     */
    public String getRxNormId() {
        return rxNormId;
    }

    /**
     * @param rxNormId the rxNormId to set
     */
    public void setRxNormId(String rxNormId) {
        this.rxNormId = rxNormId;
        this._id = rxNormId;
    }

    /**
     * @return the nui
     */
    public String getNui() {
        return nui;
    }

    /**
     * @param nui the nui to set
     */
    public void setNui(String nui) {
        this.nui = nui;
    }

    /**
     * @return the ndfrtName
     */
    public String getNdfrtName() {
        return ndfrtName;
    }

    /**
     * @param ndfrtName the ndfrtName to set
     */
    public void setNdfrtName(String ndfrtName) {
        this.ndfrtName = ndfrtName;
    }

    /**
     * @return the drugName
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * @param drugName the drugName to set
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }
}
