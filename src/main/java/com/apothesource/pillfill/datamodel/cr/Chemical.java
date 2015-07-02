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
package com.apothesource.pillfill.datamodel.cr;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}chemical_name"/>
 *         &lt;element name="normalized_chemical_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="unii" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="NUI" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element ref="{}class_subdivision_notes" minOccurs="0"/>
 *         &lt;element name="class_subdivision_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="drug_class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element ref="{}drug_form" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
public class Chemical implements Serializable {

    protected String chemicalName;
    protected String normalizedChemicalName;
    protected List<String> unii;
    protected String nui;
    protected ClassSubdivisionNotes classSubdivisionNotes;
    protected String classSubdivisionName;
    protected List<String> drugClass;
    protected List<DrugForm> drugForm;

    /**
     * Gets the value of the chemicalName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getChemicalName() {
        return chemicalName;
    }

    /**
     * Sets the value of the chemicalName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setChemicalName(String value) {
        this.chemicalName = value;
    }

    /**
     * Gets the value of the normalizedChemicalName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNormalizedChemicalName() {
        return normalizedChemicalName;
    }

    /**
     * Sets the value of the normalizedChemicalName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNormalizedChemicalName(String value) {
        this.normalizedChemicalName = value;
    }

    /**
     * Gets the value of the unii property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unii property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnii().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getUnii() {
        if (unii == null) {
            unii = new ArrayList<String>();
        }
        return this.unii;
    }

    /**
     * Gets the value of the nui property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNUI() {
        return nui;
    }

    /**
     * Sets the value of the nui property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNUI(String value) {
        this.nui = value;
    }

    /**
     * Gets the value of the classSubdivisionNotes property.
     *
     * @return possible object is
     * {@link ClassSubdivisionNotes }
     */
    public ClassSubdivisionNotes getClassSubdivisionNotes() {
        return classSubdivisionNotes;
    }

    /**
     * Sets the value of the classSubdivisionNotes property.
     *
     * @param value allowed object is
     *              {@link ClassSubdivisionNotes }
     */
    public void setClassSubdivisionNotes(ClassSubdivisionNotes value) {
        this.classSubdivisionNotes = value;
    }

    /**
     * Gets the value of the classSubdivisionName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getClassSubdivisionName() {
        return classSubdivisionName;
    }

    /**
     * Sets the value of the classSubdivisionName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setClassSubdivisionName(String value) {
        this.classSubdivisionName = value;
    }

    /**
     * Gets the value of the drugClass property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drugClass property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrugClass().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getDrugClass() {
        if (drugClass == null) {
            drugClass = new ArrayList<String>();
        }
        return this.drugClass;
    }

    /**
     * Gets the value of the drugForm property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drugForm property.
     * <p>
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrugForm().add(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrugForm }
     */
    public List<DrugForm> getDrugForm() {
        if (drugForm == null) {
            drugForm = new ArrayList<DrugForm>();
        }
        return this.drugForm;
    }

}
