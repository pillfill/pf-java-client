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
package com.apothesource.pillfill.datamodel.ndfrt;

import java.io.Serializable;

public class FullConcept implements Serializable {

    private static final long serialVersionUID = -1L;

    private String conceptName;

    private String conceptKind;

    private String conceptNui;

    private ParentConcepts parentConcepts;

    private AncestorConcepts ancestorConcepts;

    private ChildConcepts childConcepts = new ChildConcepts();

    private GroupProperties groupProperties = new GroupProperties();

    private GroupRoles groupRoles = new GroupRoles();

    private GroupAssociations groupAssociations;

    private GroupInteractingDrugs groupInteractingDrugs;

    private String id;

    private String rev;

    private String code;

    private SplList splList;


    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getConceptName() {
        return this.conceptName;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setConceptName(String conceptName) {
        this.conceptName = conceptName;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getConceptKind() {
        return this.conceptKind;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setConceptKind(String conceptKind) {
        this.conceptKind = conceptKind;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getConceptNui() {
        return this.conceptNui;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setConceptNui(String conceptNui) {
        this.conceptNui = conceptNui;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.ParentConcepts
     */
    public ParentConcepts getParentConcepts() {
        return this.parentConcepts;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.ParentConcepts
     */
    public void setParentConcepts(ParentConcepts parentConcepts) {
        this.parentConcepts = parentConcepts;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.ChildConcepts
     */
    public ChildConcepts getChildConcepts() {
        return this.childConcepts;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.ChildConcepts
     */
    public void setChildConcepts(ChildConcepts childConcepts) {
        this.childConcepts = childConcepts;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.GroupProperties
     */
    public GroupProperties getGroupProperties() {
        return this.groupProperties;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.GroupProperties
     */
    public void setGroupProperties(GroupProperties groupProperties) {
        this.groupProperties = groupProperties;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.GroupRoles
     */
    public GroupRoles getGroupRoles() {
        return this.groupRoles;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.GroupRoles
     */
    public void setGroupRoles(GroupRoles groupRoles) {
        this.groupRoles = groupRoles;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.GroupAssociations
     */
    public GroupAssociations getGroupAssociations() {
        return this.groupAssociations;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.GroupAssociations
     */
    public void setGroupAssociations(GroupAssociations groupAssociations) {
        this.groupAssociations = groupAssociations;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.GroupInteractingDrugs
     */
    public GroupInteractingDrugs getGroupInteractingDrugs() {
        return this.groupInteractingDrugs;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.GroupInteractingDrugs
     */
    public void setGroupInteractingDrugs(GroupInteractingDrugs groupInteractingDrugs) {
        this.groupInteractingDrugs = groupInteractingDrugs;
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
     * @returns java.lang.String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.SplList
     */
    public SplList getSplList() {
        return this.splList;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.SplList
     */
    public void setSplList(SplList splList) {
        this.splList = splList;
    }

    public AncestorConcepts getAncestorConcepts() {
        return ancestorConcepts;
    }

    public void setAncestorConcepts(AncestorConcepts ancestorConcepts) {
        this.ancestorConcepts = ancestorConcepts;
    }

}
