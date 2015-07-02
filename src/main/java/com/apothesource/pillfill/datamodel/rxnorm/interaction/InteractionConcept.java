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

/**
 * <p>Java class for InteractionConcept complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType name="InteractionConcept">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="minConceptItem" type="{}MinConceptItem"/>
 *         &lt;element name="sourceConceptItem" type="{}SourceConceptItem"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
public class InteractionConcept {

    protected MinConceptItem minConceptItem;
    protected SourceConceptItem sourceConceptItem;

    /**
     * Gets the value of the minConceptItem property.
     *
     * @return possible object is
     * {@link MinConceptItem }
     */
    public MinConceptItem getMinConceptItem() {
        return minConceptItem;
    }

    /**
     * Sets the value of the minConceptItem property.
     *
     * @param value allowed object is
     *              {@link MinConceptItem }
     */
    public void setMinConceptItem(MinConceptItem value) {
        this.minConceptItem = value;
    }

    /**
     * Gets the value of the sourceConceptItem property.
     *
     * @return possible object is
     * {@link SourceConceptItem }
     */
    public SourceConceptItem getSourceConceptItem() {
        return sourceConceptItem;
    }

    /**
     * Sets the value of the sourceConceptItem property.
     *
     * @param value allowed object is
     *              {@link SourceConceptItem }
     */
    public void setSourceConceptItem(SourceConceptItem value) {
        this.sourceConceptItem = value;
    }

}
