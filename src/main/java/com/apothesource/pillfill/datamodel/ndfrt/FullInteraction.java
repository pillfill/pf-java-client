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

public class FullInteraction implements Serializable {

    private static final long serialVersionUID = -1L;

    private InteractionPair interactionPair;

    private String comment;

    private String interactionCount;

    private InteractionTripleGroup interactionTripleGroup;


    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.InteractionPair
     */
    public InteractionPair getInteractionPair() {
        return this.interactionPair;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.InteractionPair
     */
    public void setInteractionPair(InteractionPair interactionPair) {
        this.interactionPair = interactionPair;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * public getter
     *
     * @returns java.lang.String
     */
    public String getInteractionCount() {
        return this.interactionCount;
    }

    /**
     * public setter
     *
     * @param java.lang.String
     */
    public void setInteractionCount(String interactionCount) {
        this.interactionCount = interactionCount;
    }

    /**
     * public getter
     *
     * @returns com.pillfill.datamodel.ndfrt.InteractionTripleGroup
     */
    public InteractionTripleGroup getInteractionTripleGroup() {
        return this.interactionTripleGroup;
    }

    /**
     * public setter
     *
     * @param com.pillfill.datamodel.ndfrt.InteractionTripleGroup
     */
    public void setInteractionTripleGroup(InteractionTripleGroup interactionTripleGroup) {
        this.interactionTripleGroup = interactionTripleGroup;
    }

}
