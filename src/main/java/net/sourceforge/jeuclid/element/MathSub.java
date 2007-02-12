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

package net.sourceforge.jeuclid.element;

import java.awt.Graphics2D;

import net.sourceforge.jeuclid.MathBase;
import net.sourceforge.jeuclid.element.generic.AbstractMathElement;
import net.sourceforge.jeuclid.element.generic.MathElement;

/**
 * This class arrange an element lower to an other element.
 * 
 */
public class MathSub extends AbstractMathElement {

    private static final double DY = 0.43 / 2;

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "msub";

    /**
     * Value of subscriptshift property.
     */
    private int m_subscriptshift = 0;

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public MathSub(final MathBase base) {
        super(base);
    }

    /**
     * Sets value of subscriptshift.
     * 
     * @param subscriptshift
     *            Value of subscriptshift property.
     */
    public void setSubScriptShift(final int subscriptshift) {
        this.m_subscriptshift = subscriptshift;
    }

    /**
     * Gets value of subscriptshift.
     * 
     * @return Value of subscriptshift property.
     */
    public int getSubScriptShift() {
        return this.m_subscriptshift;
    }

    /**
     * Paints this element.
     * 
     * @param g
     *            The graphics context to use for painting.
     * @param posX
     *            The first left position for painting.
     * @param posY
     *            The position of the baseline.
     */
    @Override
    public void paint(final Graphics2D g, final int posX, final int posY) {
        super.paint(g, posX, posY);
        final MathElement e1 = this.getMathElement(0);
        final MathElement e2 = this.getMathElement(1);

        final int middleshift = (int) (e1.getHeight(g) * MathSub.DY);

        int e1DescentHeight = e1.getDescentHeight(g);
        if (e1DescentHeight == 0) {
            e1DescentHeight = this.getFontMetrics(g).getDescent();
        }
        int e1AscentHeight = e1.getAscentHeight(g);
        if (e1AscentHeight == 0) {
            e1AscentHeight = this.getFontMetrics(g).getAscent();
        }

        final int posY1 = posY + e1DescentHeight + e2.getAscentHeight(g)
                - middleshift - 1;

        e1.paint(g, posX, posY);
        e2.paint(g, posX + e1.getWidth(g), posY1);
    }

    /** {@inheritDoc} */
    @Override
    public int calculateWidth(final Graphics2D g) {
        return this.getMathElement(0).getWidth(g)
                + this.getMathElement(1).getWidth(g);
    }

    /** {@inheritDoc} */
    @Override
    public int calculateAscentHeight(final Graphics2D g) {
        return this.getMathElement(0).getAscentHeight(g);
    }

    /** {@inheritDoc} */
    @Override
    public int calculateDescentHeight(final Graphics2D g) {
        final int e2h = Math
                .max(
                        this.getMathElement(1).getHeight(g)
                                - (int) (this.getMathElement(0).getHeight(g) * MathSub.DY),
                        0);
        return this.getMathElement(0).getDescentHeight(g) + e2h;
    }

    /** {@inheritDoc} */
    @Override
    public int getScriptlevelForChild(final MathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return this.getAbsoluteScriptLevel();
        } else {
            return this.getAbsoluteScriptLevel() + 1;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isChildBlock(final MathElement child) {
        if (child.isSameNode(this.getFirstChild())) {
            return super.isChildBlock(child);
        } else {
            return false;
        }
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return MathSub.ELEMENT;
    }

}
