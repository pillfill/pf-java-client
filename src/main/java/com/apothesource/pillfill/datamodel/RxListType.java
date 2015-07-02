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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RxListType implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<String> currentList = new ArrayList<String>();

    private List<String> hiddenList = new ArrayList<String>();

    private List<String> inactiveList = new ArrayList<String>();

    private List<String> processedList = new ArrayList<String>();

    private List<String> otcList = new ArrayList<String>();


    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getCurrentList() {
        return this.currentList;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setCurrentList(List<String> currentList) {
        this.currentList = currentList;
    }

    public int getTotalListCount() {
        return currentList.size() + inactiveList.size() + hiddenList.size();
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getHiddenList() {
        return this.hiddenList;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setHiddenList(List<String> hiddenList) {
        this.hiddenList = hiddenList;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getInactiveList() {
        return this.inactiveList;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setInactiveList(List<String> inactiveList) {
        this.inactiveList = inactiveList;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getProcessedList() {
        return this.processedList;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setProcessedList(List<String> processedList) {
        this.processedList = processedList;
    }

    /**
     * public getter
     *
     * @returns java.util.List<java.lang.String>
     */
    public List<String> getOtcList() {
        return this.otcList;
    }

    /**
     * public setter
     *
     * @param java.util.List<java.lang.String>
     */
    public void setOtcList(List<String> otcList) {
        this.otcList = otcList;
    }

}
