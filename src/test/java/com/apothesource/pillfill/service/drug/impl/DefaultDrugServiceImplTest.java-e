/*
 *
 *  * The MIT License
 *  *
 *  * Copyright {$YEAR} Apothesource, Inc.
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.apothesource.pillfill.service.drug.impl;

import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.ndfrt.FullConcept;
import com.apothesource.pillfill.datamodel.spl.SplInformation;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import rx.Observable;

/**
 * Created by Michael Ramirez on 6/12/15. Copyright 2015, Apothesource, Inc. All
 * Rights Reserved.
 */
public class DefaultDrugServiceImplTest {

    DefaultDrugServiceImpl defaultDrugServiceImpl = new DefaultDrugServiceImpl();

    public DefaultDrugServiceImplTest() {

    }

    @Test
    public void testGetRxNormIdForDrugNdc() throws Exception {
        assertThat(defaultDrugServiceImpl.getRxNormIdForDrugNdc("54868350903").toBlocking().last(), is("104896"));

    }

    @Test
    @Ignore("Service needs to be added on the server first.")
    public void testGetNdfrtConceptsByUnii() throws Exception {
        assertThat(defaultDrugServiceImpl.getNdfrtConceptsByUnii("362O9ITL9D").toBlocking().last().getConceptName(), is("ACETAMINOPHEN"));
    }

    @Test
    @Ignore("Service needs to be added on the server first.")
    public void testGetNdfrtConceptsByBadUnii() throws Exception {
        defaultDrugServiceImpl.getNdfrtConceptsByUnii("XXXXXXXX").toBlocking().forEach(fullConcept -> fail("Bad UNII should not return a concept."));
    }

    @Test
    public void testGetSplInformation() throws Exception {
        List<? extends SplInformation> splList = defaultDrugServiceImpl.getConceptList(new SplInformation().getClass(), "8165CAB8-5E49-4A17-8C86-E057B218A7E7")
                .toList()
                .toBlocking()
                .first();
        assertThat("Received a SPLEntry for the provided SPL ID.", splList.size(), is(1));
        Observable.from(splList).forEach(spl -> {
            assertNotNull(spl.getSplEntry());
        });
    }

    @Test
    public void testGetNdfrtInformation() throws Exception {
        List<? extends FullConcept> conceptList = defaultDrugServiceImpl
                .getConceptList(new FullConcept().getClass(), "N0000146197", "N0000146196")
                .toList()
                .toBlocking()
                .first();
        assertThat("Received 2 concepts from the server.", conceptList.size(), is(2));
        Observable.from(conceptList).forEach(fc -> {
            assertNotNull(fc.getConceptName());
            assertNotNull(fc.getConceptNui());
        });

    }

    @Test
    public void testGetRxList() throws Exception {
        List<? extends PrescriptionType> rxList = defaultDrugServiceImpl
                .getConceptList(new PrescriptionType().getClass(),
                        "00090D9ABEAFFDE010EAD13E8E845AF2232976A745C710F8801EC8F35BCD7494",
                        "00153170CDEC5571789987505799C59EB63C06BCC821B23E3D70A566F3F95A70",
                        "002C0ABB528855B112BD9E50282B19CD7A01A4E95657C3090BA6F6042BC89208")
                .toList()
                .toBlocking()
                .first();
        assertThat("Received 3 prescriptions from the server.", rxList.size(), is(3));
        Observable.from(rxList).forEach(rx -> {
            assertNotNull(rx.getMedicationName());
            assertNotNull(rx.getNdc());
        });
    }
}
