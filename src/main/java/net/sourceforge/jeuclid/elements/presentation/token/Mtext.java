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

package net.sourceforge.jeuclid.elements.presentation.token;

import net.sourceforge.jeuclid.MathBase;

import org.w3c.dom.mathml.MathMLPresentationToken;

/**
 * This class presents text in a equation and contains some utility methods.
 * 
 * @author Unknown
 * @author Max Berger
 * @version $Revision$
 */
public class Mtext extends AbstractTokenWithStandardLayout implements
        MathMLPresentationToken {
    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "mtext";

    /**
     * Creates a math element.
     * 
     * @param base
     *            The base for the math element tree.
     */
    public Mtext(final MathBase base) {
        super(base);
    }

    /** {@inheritDoc} */
    public String getTagName() {
        return Mtext.ELEMENT;
    }

}