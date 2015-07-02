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
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {

    private static final long serialVersionUID = -1L;
    public transient Integer productId;
    private String name;
    private String form;
    private String route;
    private boolean isOtc = false;
    private List<Ingredient> ingredient;
    private InactiveIngredients inactiveIngredients = new InactiveIngredients();
    private List<Pkg> pkg;
    private String rxnId, rxnScdId;
    private String splId;
    private String genericMedicine;
    private String deaSchedule;
    private Characteristics characteristics;

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getName() {
        return this.name;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getForm() {
        return this.form;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getRoute() {
        return this.route;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setRoute(String route) {
        this.route = route;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.spl.Ingredient>
     */
    public List<Ingredient> getIngredient() {
        return this.ingredient;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.spl.Ingredient>
     */
    public void setIngredient(List<Ingredient> ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * public getter
     *
     * @returns java.util.List<com.pillfill.datamodel.spl.Pkg>
     */
    public List<Pkg> getPkg() {
        return this.pkg;
    }

    /**
     * public setter
     *
     * @param java.util.List<com.pillfill.datamodel.spl.Pkg>
     */
    public void setPkg(List<Pkg> pkg) {
        this.pkg = pkg;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.spl.Characteristics
     */
    public Characteristics getCharacteristics() {
        return this.characteristics;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.spl.Characteristics
     */
    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public InactiveIngredients getInactiveIngredients() {
        return inactiveIngredients;
    }

    public void setInactiveIngredients(InactiveIngredients inactiveIngredients) {
        this.inactiveIngredients = inactiveIngredients;
    }

    public String getRxnScdId() {
        return rxnScdId;
    }

    public void setRxnScdId(String rxnScdId) {
        this.rxnScdId = rxnScdId;
    }

    public String getRxnId() {
        return rxnId;
    }

    public void setRxnId(String rxnId) {
        this.rxnId = rxnId;
    }

    public boolean isOtc() {
        return isOtc;
    }

    public void setOtc(boolean isOtc) {
        this.isOtc = isOtc;
    }

    public String getSplId() {
        return splId;
    }

    public void setSplId(String splId) {
        this.splId = splId;
    }

    public String getDeaSchedule() {
        return deaSchedule;
    }

    public void setDeaSchedule(String deaSchedule) {
        this.deaSchedule = deaSchedule;
    }

    public String getGenericMedicine() {
        return genericMedicine;
    }

    public void setGenericMedicine(String genericMedicine) {
        this.genericMedicine = genericMedicine;
    }

    public static class InactiveIngredients implements Serializable {
        private static final long serialVersionUID = -1L;

        private List<Ingredient> ingredient = new ArrayList<Ingredient>();

        public List<Ingredient> getIngredient() {
            return ingredient;
        }

        public void setIngredient(List<Ingredient> ingredient) {
            this.ingredient = ingredient;
        }
    }

}
