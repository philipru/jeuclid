/*
 * Copyright 2007 - 2007 JEuclid, http://jeuclid.sf.net
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

package net.sourceforge.jeuclid.converter;

import java.awt.Dimension;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;

import net.sourceforge.jeuclid.LayoutContext;
import net.sourceforge.jeuclid.elements.generic.DocumentElement;

import org.w3c.dom.Document;

/**
 * Converter for output formats supported by FreeHEP.
 * 
 * @author Max Berger
 * @version $Revision$
 */
public class FreeHepConverter implements ConverterPlugin {

    private final Constructor<?> streamConst;

    FreeHepConverter(final Class<?> converterClass)
            throws NoSuchMethodException {

        this.streamConst = converterClass.getConstructor(OutputStream.class,
                Dimension.class);
    }

    /** {@inheritDoc} */
    public Dimension convert(final DocumentElement doc,
            final LayoutContext context, final OutputStream outStream)
            throws IOException {
        // final Properties p = new Properties();
        // // p.setProperty("PageSize","A5");
        // VectorGraphics temp;
        // try {
        // temp = (VectorGraphics) this.streamConst.newInstance(
        // new ByteArrayOutputStream(), new Dimension(1, 1));
        //
        // final Dimension size = new Dimension((int) Math.ceil(base
        // .getWidth(temp)), (int) Math.ceil(base.getHeight(temp)));
        //
        // final VectorGraphics g = (VectorGraphics) this.streamConst
        // .newInstance(outStream, size);
        // g.setProperties(p);
        // g.startExport();
        // base.paint(g);
        // g.endExport();
        //
        // return size;
        // } catch (final InstantiationException e) {
        // throw new IOException();
        // } catch (final IllegalAccessException e) {
        // throw new IOException();
        // } catch (final InvocationTargetException e) {
        // throw new IOException();
        // }
        return null;
    }

    /** {@inheritDoc} */
    public Document convert(final DocumentElement doc,
            final LayoutContext context) {
        return null;
    }

}
