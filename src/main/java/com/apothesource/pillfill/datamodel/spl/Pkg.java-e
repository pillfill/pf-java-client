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

public class Pkg implements Serializable {

    private static final long serialVersionUID = -1L;

    private String quantity;

    private String ndc;
    private ArrayList<String> ndcs;

    private List<String> splLabelImage;

    private List<String> splPillImage;

    private String form;

    private List<String> upc;


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

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getNdc() {
        return this.ndc;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setNdc(String ndc) {
        this.ndc = ndc;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getSplLabelImage() {
        if (splLabelImage == null) splLabelImage = new ArrayList<String>();
        return this.splLabelImage;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setSplLabelImage(List<String> splLabelImage) {
        this.splLabelImage = splLabelImage;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getSplPillImage() {
        if (splPillImage == null) {
            splPillImage = new ArrayList<>();
        }
        return this.splPillImage;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setSplPillImage(List<String> splPillImage) {
        this.splPillImage = splPillImage;
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
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getUpc() {
        return this.upc;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setUpc(List<String> upc) {
        this.upc = upc;
    }

    public ArrayList<String> getNdcs() {
        if (ndcs == null) ndcs = new ArrayList<>();
        return ndcs;
    }

    public void setNdcs(ArrayList<String> ndcs) {
        this.ndcs = ndcs;
    }
}
