/*
 * Copyright 2002 - 2007 JEuclid, http://jeuclid.sf.net
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* $Id$ */

package net.sourceforge.jeuclid.elements.presentation.general;

import java.awt.Graphics2D;
import java.util.List;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.elements.JEuclidElement;
import net.sourceforge.jeuclid.elements.presentation.AbstractContainer;
import net.sourceforge.jeuclid.elements.support.ElementListSupport;

import org.w3c.dom.mathml.MathMLPresentationContainer;

/**
 * Represents a Math element that is painted and defined through its children.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public abstract class AbstractMathElementWithChildren extends
        AbstractContainer implements MathMLPresentationContainer {
    /**
     * S Default constructor.
     * 
     * @param base
     *            MathBase to use.
     */
    public AbstractMathElementWithChildren(final MathBase base) {
        super(base);
    }

    private List<JEuclidElement> getChildrenAsList() {
        return ElementListSupport.createListOfChildren(this);
    }

    /**
     * Paint all children. To be called from within a paint() method.
     * 
     * @param g
     *            Graphics2D context
     * @param posX
     *            x-offset to start painting
     * @param posY
     *            y-offset to start painting
     */
    protected void paintChildren(final Graphics2D g, final float posX,
            final float posY) {
        super.paint(g, posX, posY);
        ElementListSupport.paint(g, posX, posY, this.getChildrenAsList());
    }

    /** {@inheritDoc} */
    public float calculateChildrenWidth(final Graphics2D g) {
        return ElementListSupport.getWidth(g, this.getChildrenAsList());
    }

    /** {@inheritDoc} */
    public float calculateChildrenAscentHeight(final Graphics2D g) {
        return ElementListSupport
                .getAscentHeight(g, this.getChildrenAsList());
    }

    /** {@inheritDoc} */
    public float calculateChildrenDescentHeight(final Graphics2D g) {
        return ElementListSupport.getDescentHeight(g, this
                .getChildrenAsList());
    }

}