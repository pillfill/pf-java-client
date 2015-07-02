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

import com.apothesource.pillfill.datamodel.ndfrt.FullConcept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DrugAlertType implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<String> ndc;

    private String type;

    private String title;

    private boolean critical = false;

    private List<String> reason;

    private List<String> relatedRxIds = new ArrayList<>();

    private List<String> resolution;

    private String additionalInfoUrl;

    private FullConcept concept;

    private Date dateSince;

    private String key;

    private boolean dismissed = false;


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
    public String getType() {
        return this.type;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getReason() {
        return this.reason;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setReason(List<String> reason) {
        this.reason = reason;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getResolution() {
        return this.resolution;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setResolution(List<String> resolution) {
        this.resolution = resolution;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getAdditionalInfoUrl() {
        return this.additionalInfoUrl;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setAdditionalInfoUrl(String additionalInfoUrl) {
        this.additionalInfoUrl = additionalInfoUrl;
    }

    /**
     * public getter
     *
     * @returns java.util.Date
     */
    public Date getDateSince() {
        return this.dateSince;
    }

    /**
     * public setter
     *
     * @param java.util.Date
     */
    public void setDateSince(Date dateSince) {
        this.dateSince = dateSince;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String conceptNui) {
        this.key = conceptNui;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FullConcept getConcept() {
        return concept;
    }

    public void setConcept(FullConcept concept) {
        this.concept = concept;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public List<String> getRelatedNuis() {
        ArrayList<String> retList = new ArrayList<String>();
        String[] components = key.split("[\\+D]"); //D is included due to a bug in the interaction key generation server side
        for (String component : components) {
            if (component.matches("N[\\d]{10}")) {
                retList.add(component);
            }
        }
        return retList;
    }

    public List<String> getRelatedRxIds() {
        return relatedRxIds;
    }

    public void setRelatedRxIds(List<String> relatedRxIds) {
        this.relatedRxIds = relatedRxIds;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof DrugAlertType) {
            DrugAlertType other = (DrugAlertType) o;
            return other.getKey().equalsIgnoreCase(getKey());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
