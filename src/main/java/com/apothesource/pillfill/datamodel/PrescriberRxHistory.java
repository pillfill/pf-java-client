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
import java.util.Collections;
import java.util.HashMap;

public class PrescriberRxHistory implements Serializable {
    public String _id;
    public HashMap<String, Integer> drugRxCount = new HashMap<String, Integer>();

    public ArrayList<Pair<String, Integer>> getSortedRxHistory() {
        ArrayList<Pair<String, Integer>> retList = new ArrayList<>();
        for (String drug : drugRxCount.keySet()) {
            retList.add(new Pair<>(drug, drugRxCount.get(drug)));
        }
        Collections.sort(retList, (lhs, rhs) -> lhs.second - rhs.second);
        Collections.reverse(retList);
        return retList;
    }

    public ArrayList<String> getSortedDrugList() {
        ArrayList<String> retList = new ArrayList<String>();
        for (Pair<String, Integer> rxs : getSortedRxHistory()) {
            retList.add(rxs.first + " (" + rxs.second + ")");
            if (retList.size() >= 10) break;
        }
        return retList;
    }
}