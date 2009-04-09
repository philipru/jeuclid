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

package euclid.elements.content.semantic;


import org.w3c.dom.Node;
import org.w3c.dom.mathml.MathMLAnnotationElement;
import euclid.elements.AbstractInvisibleJEuclidElement;

/**
 * This class represents a annotation element.
 * 
 * @version $Revision$
 */
public final class Annotation extends AbstractInvisibleJEuclidElement
        implements MathMLAnnotationElement {

    /**
     * The XML element from this class.
     */
    public static final String ELEMENT = "annotation";

    /** The encoding attribute. */
    public static final String ATTR_ENCODING = "encoding";

    private static final long serialVersionUID = 1L;

    /**
     * Creates a math element.
     */
    public Annotation() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    protected Node newNode() {
        return new Annotation();
    }

    /** {@inheritDoc} */
    public String getBody() {
        return this.getText();
    }

    /** {@inheritDoc} */
    public String getEncoding() {
        return this.getMathAttribute(Annotation.ATTR_ENCODING);
    }

    /** {@inheritDoc} */
    public void setBody(final String body) {
        this.setTextContent(body);
    }

    /** {@inheritDoc} */
    public void setEncoding(final String encoding) {
        this.setAttribute(Annotation.ATTR_ENCODING, encoding);
    }

}
