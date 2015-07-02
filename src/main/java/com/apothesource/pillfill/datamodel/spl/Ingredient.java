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

public class Ingredient implements Serializable {

    private static final long serialVersionUID = -1L;

    private String quantity;

    private String substance;

    private String unii;

    private transient Float totalLoad = 0f;


    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getQuantity() {
        return this.quantity;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Float getLoad() {
        try {
            Float load = Float.parseFloat(this.quantity.split("[\\s]")[0]);
            return load;
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public void addLoad(Ingredient ing) {
        if (this.equals(ing)) {
            totalLoad += ing.getLoad();
        }
    }

    public Float getTotalLoad() {
        return totalLoad;
    }

    public Object clone() {
        Ingredient i = new Ingredient();
        i.quantity = quantity;
        i.substance = substance;
        i.unii = unii;
        i.totalLoad = totalLoad;
        return i;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || !(other instanceof Ingredient)) {
            return false;
        }
        return hashCode() == other.hashCode();
    }

    @Override
    public int hashCode() {
        return unii.hashCode();
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getSubstance() {
        return this.substance;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setSubstance(String substance) {
        this.substance = substance;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getUnii() {
        return this.unii;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setUnii(String unii) {
        this.unii = unii;
    }

    public String toString() {
        return substance + "(" + unii + ")";
    }
}
