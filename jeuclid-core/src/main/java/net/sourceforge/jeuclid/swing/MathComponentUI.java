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

package net.sourceforge.jeuclid.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Component.BaselineResizeBehavior;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import net.sourceforge.jeuclid.Defense;
import net.sourceforge.jeuclid.MutableLayoutContext;
import net.sourceforge.jeuclid.layout.JEuclidView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

/**
 * See
 * http://today.java.net/pub/a/today/2007/02/22/how-to-write-custom-swing-component.html
 * for details.
 * 
 * @version $Revision$
 * 
 */
public class MathComponentUI extends ComponentUI implements
        PropertyChangeListener {

    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory
            .getLog(MathComponentUI.class);

    private JMathComponent mathComponent;

    /**
     * Reference to layout tree.
     */
    private JEuclidView jEuclidView;

    /** Reference to document. */
    private Node document;

    private Dimension preferedSize;

    /**
     * Creates a new UI.
     * 
     */
    public MathComponentUI() {
        // nothing to do
    }

    @Override
    public BaselineResizeBehavior getBaselineResizeBehavior(final JComponent c) {
        switch (((JMathComponent) c).getVerticalAlignment()) {
        case SwingConstants.TOP:
            return Component.BaselineResizeBehavior.CONSTANT_ASCENT;
        case SwingConstants.BOTTOM:
            return Component.BaselineResizeBehavior.CONSTANT_DESCENT;
        case SwingConstants.CENTER:
            return Component.BaselineResizeBehavior.CENTER_OFFSET;
        }
        return Component.BaselineResizeBehavior.OTHER;
    }

    /** {@inheritDoc} */
    @Override
    public void paint(final Graphics g, final JComponent c) {
        this.preferedSize = null;
        final Graphics2D g2 = (Graphics2D) g;
        // using the size seems to cause flickering is some cases
        final Dimension dim = this.mathComponent.getSize();
        final Point start = this
                .getStartPointWithBordersAndAdjustDimension(dim);
        this.paintBackground(g, dim, start);
        if (this.jEuclidView != null) {
            final Point2D alignOffset = this
                    .calculateAlignmentOffset(g2, dim);
            this.jEuclidView.draw((Graphics2D) g, (float) alignOffset.getX()
                    + start.x, (float) alignOffset.getY() + start.y);
        }

    }

    /** {@inheritDoc} */
    @Override
    public void update(final Graphics g, final JComponent c) {
        if (c.isOpaque()) {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        this.paint(g, c);
    }

    private Point2D calculateAlignmentOffset(final Graphics2D g2,
            final Dimension dim) {
        final float xo;
        if ((this.mathComponent.getHorizontalAlignment() == SwingConstants.LEADING)
                || (this.mathComponent.getHorizontalAlignment() == SwingConstants.LEFT)) {
            xo = 0.0f;
        } else if ((this.mathComponent.getHorizontalAlignment() == SwingConstants.TRAILING)
                || (this.mathComponent.getHorizontalAlignment() == SwingConstants.RIGHT)) {
            xo = dim.width - this.jEuclidView.getWidth();
        } else {
            xo = (dim.width - this.jEuclidView.getWidth()) / 2.0f;
        }
        final float yo;
        if (this.mathComponent.getVerticalAlignment() == SwingConstants.TOP) {
            yo = this.jEuclidView.getAscentHeight();
        } else if (this.mathComponent.getVerticalAlignment() == SwingConstants.BOTTOM) {
            yo = dim.height - this.jEuclidView.getDescentHeight();
        } else {
            yo = (dim.height + this.jEuclidView.getAscentHeight() - this.jEuclidView
                    .getDescentHeight()) / 2.0f;
        }
        final Point2D alignOffset = new Point2D.Float(xo, yo);
        return alignOffset;
    }

    private void paintBackground(final Graphics g, final Dimension dim,
            final Point start) {
        final Color back = this.getRealBackgroundColor();
        if (back != null) {
            g.setColor(back);
            g.fillRect(start.x, start.y, dim.width, dim.height);
        }
    }

    private Point getStartPointWithBordersAndAdjustDimension(
            final Dimension dim) {
        Point start = new Point(0, 0);
        final Border border = this.mathComponent.getBorder();
        if (border != null) {
            final Insets insets = border.getBorderInsets(this.mathComponent);
            if (insets != null) {
                dim.width -= insets.left + insets.right;
                dim.height -= insets.top + insets.bottom;
                start = new Point(insets.left, insets.top);
            }
        }
        return start;
    }

    private Color getRealBackgroundColor() {
        Color back = this.mathComponent.getBackground();
        if (this.mathComponent.isOpaque()) {
            if (back == null) {
                back = Color.WHITE;
            }
            // Remove Alpha
            back = new Color(back.getRGB());
        }
        return back;
    }

    /** {@inheritDoc} */
    @Override
    public void installUI(final JComponent c) {
        this.mathComponent = (JMathComponent) c;
        c.addPropertyChangeListener(this);
        this.installDefaults(this.mathComponent);
    }

    /**
     * Configures the default properties from L&F.
     * 
     * @param c
     *            the component
     */
    protected void installDefaults(final JMathComponent c) {
        // LookAndFeel.installColorsAndFont(c, "Label.background",
        // "Label.foreground", "Label.font");
        LookAndFeel.installProperty(c, "opaque", Boolean.FALSE);
    }

    /** {@inheritDoc} */
    @Override
    public void uninstallUI(final JComponent c) {
        c.removePropertyChangeListener(this);
        this.mathComponent = null;
    }

    /** {@inheritDoc} */
    public void propertyChange(final PropertyChangeEvent evt) {
        final String name = evt.getPropertyName();
        if (name.equals("document") || name.equals("property")) {
            final JMathComponent jc = (JMathComponent) evt.getSource();
            this.document = (Node) evt.getNewValue();
            this.redo(jc.getParameters(), (Graphics2D) jc.getGraphics());
            // jc.repaint();
        } else {
            try {
                final JMathComponent jc = (JMathComponent) evt.getSource();
                this.redo(jc.getParameters(), (Graphics2D) jc.getGraphics());
            } catch (final ClassCastException ia) {
                MathComponentUI.LOGGER.debug(ia);
            }
        }
    }

    private void redo(final MutableLayoutContext parameters,
            final Graphics2D g2d) {
        if ((this.document != null) && (g2d != null)) {
            this.jEuclidView = new JEuclidView(this.document, parameters, g2d);
        } else {
            this.jEuclidView = null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return this.getMathComponentSize(c);
    }

    protected Dimension getMathComponentSize(final JComponent c) {
        if (this.preferedSize != null) {
            return this.preferedSize;
        }
        if (this.jEuclidView == null || c.getGraphics() == null) {
            return super.getPreferredSize(c);
        } else {
            final Graphics2D g2d = (Graphics2D) c.getGraphics();
            Defense.notNull(g2d, "g2d");
            this.preferedSize = new Dimension((int) Math
                    .ceil(this.jEuclidView.getWidth()), (int) Math
                    .ceil(this.jEuclidView.getAscentHeight()
                            + this.jEuclidView.getDescentHeight()));
        }
        final Border border = c.getBorder();
        if (border != null) {
            final Insets insets = border.getBorderInsets(c);
            if (insets != null) {
                this.preferedSize.width += insets.left + insets.right;
                this.preferedSize.height += insets.top + insets.bottom;
            }
        }
        return this.preferedSize;
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getMaximumSize(final JComponent c) {
        return this.getMathComponentSize(c);
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return this.getMathComponentSize(c);
    }

}
