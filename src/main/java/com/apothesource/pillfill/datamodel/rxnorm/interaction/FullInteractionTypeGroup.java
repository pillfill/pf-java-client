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
package com.apothesource.pillfill.datamodel.rxnorm.interaction;

import java.util.ArrayList;
import java.util.List;


public class FullInteractionTypeGroup {

    protected List<FullInteractionType> fullInteractionType;
    protected String sourceDisclaimer;
    protected String sourceName;

    /**
     * Gets the value of the fullInteractionType property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fullInteractionType property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFullInteractionType().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FullInteractionType }
     */
    public List<FullInteractionType> getFullInteractionType() {
        if (fullInteractionType == null) {
            fullInteractionType = new ArrayList<FullInteractionType>();
        }
        return this.fullInteractionType;
    }

    /**
     * Gets the value of the sourceDisclaimer property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSourceDisclaimer() {
        return sourceDisclaimer;
    }

    /**
     * Sets the value of the sourceDisclaimer property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSourceDisclaimer(String value) {
        this.sourceDisclaimer = value;
    }

    /**
     * Gets the value of the sourceName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * Sets the value of the sourceName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSourceName(String value) {
        this.sourceName = value;
    }

}
