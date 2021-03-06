/*
 * @(#)GradAnim.java	1.21 06/04/14
 * 
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)GradAnim.java	1.21 06/04/14
 */

package concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d.demos.Paint;

import concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d.AnimatingControlsSurface;
import concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d.CustomControls;

import javax.swing.*;
import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;


/**
 * GradientPaint animation.
 */
public class GradAnim extends AnimatingControlsSurface {

    private static final int BASIC_GRADIENT = 0;
    private static final int LINEAR_GRADIENT = 1;
    private static final int RADIAL_GRADIENT = 2;
    private static final int FOCUS_GRADIENT = 3;

    private static final int MAX_HUE = 256 * 6;
    private animval x1, y1, x2, y2;
    private int hue = (int) (Math.random() * MAX_HUE);
    private int gradientType;


    public GradAnim() {
        setBackground(Color.white);
        setControls(new Component[]{new DemoControls(this)});
        x1 = new animval(0, 300, 2, 10);
        y1 = new animval(0, 300, 2, 10);
        x2 = new animval(0, 300, 2, 10);
        y2 = new animval(0, 300, 2, 10);
        gradientType = BASIC_GRADIENT;
    }


    @Override
    public void reset(int w, int h) {
        x1.newlimits(0, w);
        y1.newlimits(0, h);
        x2.newlimits(0, w);
        y2.newlimits(0, h);
    }


    @Override
    public void step(int w, int h) {
        x1.anim();
        y1.anim();
        x2.anim();
        y2.anim();
        hue = (hue + (int) (Math.random() * 10)) % MAX_HUE;
    }


    public static Color getColor(int hue) {
        int leg = (hue / 256) % 6;
        int step = (hue % 256) * 2;
        int falling = (step < 256) ? 255 : 511 - step;
        int rising = (step < 256) ? step : 255;
        int r, g, b;
        r = g = b = 0;
        switch (leg) {
            case 0:
                r = 255;
                break;
            case 1:
                r = falling;
                g = rising;
                break;
            case 2:
                g = 255;
                break;
            case 3:
                g = falling;
                b = rising;
                break;
            case 4:
                b = 255;
                break;
            case 5:
                b = falling;
                r = rising;
                break;
        }
        return new Color(r, g, b);
    }


    @Override
    public void render(int w, int h, Graphics2D g2) {
        float fx1 = x1.getFlt();
        float fy1 = y1.getFlt();
        float fx2 = x2.getFlt();
        float fy2 = y2.getFlt();

        if ((fx1 == fx2) && (fy1 == fy2)) {
            // just to prevent the points from being coincident
            fx2++;
            fy2++;
        }

        Color c1 = getColor(hue);
        Color c2 = getColor(hue + 256 * 3);
        Paint gp;

        switch (gradientType) {
            case BASIC_GRADIENT:
            default:
                gp = new GradientPaint(fx1, fy1, c1,
                        fx2, fy2, c2,
                        true);
                break;
            case LINEAR_GRADIENT: {
                float[] fractions = new float[]{0.0f, 0.2f, 1.0f};
                Color c3 = getColor(hue + 256 * 2);
                Color[] colors = new Color[]{c1, c2, c3};
                gp = new LinearGradientPaint(fx1, fy1,
                        fx2, fy2,
                        fractions, colors,
                        CycleMethod.REFLECT);
            }
            break;

            case RADIAL_GRADIENT: {
                float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
                Color c3 = getColor(hue + 256 * 2);
                Color c4 = getColor(hue + 256 * 4);
                Color[] colors = new Color[]{c1, c2, c3, c4};
                float radius = (float) Point2D.distance(fx1, fy1, fx2, fy2);
                gp = new RadialGradientPaint(fx1, fy1, radius,
                        fractions, colors,
                        CycleMethod.REFLECT);
            }
            break;

            case FOCUS_GRADIENT: {
                float[] fractions = {0.0f, 0.2f, 0.8f, 1.0f};
                Color c3 = getColor(hue + 256 * 4);
                Color c4 = getColor(hue + 256 * 2);
                Color[] colors = new Color[]{c1, c2, c3, c4};
                float radius = (float) Point2D.distance(fx1, fy1, fx2, fy2);
                float max = Math.max(w, h);
                // This function will map the smallest radius to
                // max/10 when the points are next to each other,
                // max when the points are max distance apart,
                // and >max when they are further apart (in which
                // case the focus clipping code in RGP will clip
                // the focus to be inside the radius).
                radius = max * (((radius / max) * 0.9f) + 0.1f);
                gp = new RadialGradientPaint(fx2, fy2, radius,
                        fx1, fy1,
                        fractions, colors,
                        CycleMethod.REPEAT);
            }
            break;
        }
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
        g2.setColor(Color.yellow);
        g2.drawLine(x1.getInt(), y1.getInt(), x2.getInt(), y2.getInt());
    }


    public class animval {
        float curval;
        float lowval;
        float highval;
        float currate;
        float lowrate;
        float highrate;

        public animval(int lowval, int highval,
                       int lowrate, int highrate) {
            this.lowval = lowval;
            this.highval = highval;
            this.lowrate = lowrate;
            this.highrate = highrate;
            this.curval = randval(lowval, highval);
            this.currate = randval(lowrate, highrate);
        }

        public float randval(float low, float high) {
            return (float) (low + Math.random() * (high - low));
        }

        public float getFlt() {
            return curval;
        }

        public int getInt() {
            return (int) curval;
        }

        public void anim() {
            curval += currate;
            clip();
        }

        public void clip() {
            if (curval > highval) {
                curval = highval - (curval - highval);
                if (curval < lowval) {
                    curval = highval;
                }
                currate = -randval(lowrate, highrate);
            } else if (curval < lowval) {
                curval = lowval + (lowval - curval);
                if (curval > highval) {
                    curval = lowval;
                }
                currate = randval(lowrate, highrate);
            }
        }

        public void newlimits(int lowval, int highval) {
            this.lowval = lowval;
            this.highval = highval;
            clip();
        }
    }


    public static void main(String argv[]) {
        createDemoFrame(new GradAnim());
    }


    class DemoControls extends CustomControls implements ActionListener {

        GradAnim demo;
        JComboBox combo;

        public DemoControls(GradAnim demo) {
            super(demo.name);
            this.demo = demo;
            combo = new JComboBox();
            combo.addActionListener(this);
            combo.addItem("2-color GradientPaint");
            combo.addItem("3-color LinearGradientPaint");
            combo.addItem("4-color RadialGradientPaint");
            combo.addItem("4-color RadialGradientPaint with focus");
            combo.setSelectedIndex(0);
            add(combo);
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            int index = combo.getSelectedIndex();
            if (index >= 0) {
                demo.gradientType = index;
            }
            if (demo.animating.thread == null) {
                demo.repaint();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 41);
        }


        @Override
        public void run() {
            Thread me = Thread.currentThread();
            while (thread == me) {
                for (int i = 0; i < combo.getItemCount(); i++) {
                    combo.setSelectedIndex(i);
                    try {
                        thread.sleep(4444);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            thread = null;
        }
    }
}
