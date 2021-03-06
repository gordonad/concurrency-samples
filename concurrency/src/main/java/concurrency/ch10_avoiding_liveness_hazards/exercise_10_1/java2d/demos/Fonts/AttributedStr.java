/*
 * @(#)AttributedStr.java	1.15 06/08/29
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
 * @(#)AttributedStr.java	1.15 06/08/29
 */

package concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d.demos.Fonts;

import concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d.Surface;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import static java.awt.Font.*;
import static java.awt.font.TextAttribute.*;


/**
 * Demonstrates how to build an AttributedString and then render the
 * string broken over lines.
 */
public class AttributedStr extends Surface {

    static Color black = new Color(20, 20, 20);
    static Color blue = new Color(94, 105, 176);
    static Color yellow = new Color(255, 255, 140);
    static Color red = new Color(149, 43, 42);
    static Color white = new Color(240, 240, 255);
    static String text = "  A quick brown  fox  jumped  over the lazy duke  ";
    static AttributedString as = new AttributedString(text);
    static AttributedCharacterIterator aci;

    static {
        Shape shape = new Ellipse2D.Double(0, 25, 12, 12);
        ShapeGraphicAttribute sga = new
                ShapeGraphicAttribute(shape, GraphicAttribute.TOP_ALIGNMENT, false);
        as.addAttribute(CHAR_REPLACEMENT, sga, 0, 1);


        Font font = new Font("sanserif", BOLD | ITALIC, 20);
        int index = text.indexOf("quick");
        as.addAttribute(FONT, font, index, index + 5);

        index = text.indexOf("brown");
        font = new Font("serif", BOLD, 20);
        as.addAttribute(FONT, font, index, index + 5);
        as.addAttribute(FOREGROUND, red, index, index + 5);

        index = text.indexOf("fox");
        AffineTransform fontAT = new AffineTransform();
        fontAT.rotate(Math.toRadians(10));
        Font fx = new Font("serif", BOLD, 30).deriveFont(fontAT);
        as.addAttribute(FONT, fx, index, index + 1);
        as.addAttribute(FONT, fx, index + 1, index + 2);
        as.addAttribute(FONT, fx, index + 2, index + 3);

        fontAT.setToRotation(Math.toRadians(-4));
        fx = font.deriveFont(fontAT);
        index = text.indexOf("jumped");
        as.addAttribute(FONT, fx, index, index + 6);

        font = new Font("serif", BOLD | ITALIC, 30);
        index = text.indexOf("over");
        as.addAttribute(UNDERLINE, UNDERLINE_ON, index, index + 4);
        as.addAttribute(FOREGROUND, white, index, index + 4);
        as.addAttribute(FONT, font, index, text.length());

        font = new Font("dialog", PLAIN, 20);
        int i = text.indexOf("duke");
        as.addAttribute(FONT, font, index, i - 1);

        BufferedImage bi = new BufferedImage(4, 4, BufferedImage.TYPE_INT_ARGB);
        bi.setRGB(0, 0, 0xffffffff);
        TexturePaint tp = new TexturePaint(bi, new Rectangle(0, 0, 4, 4));
        as.addAttribute(BACKGROUND, tp, i, i + 4);
        font = new Font("serif", BOLD, 40);
        as.addAttribute(FONT, font, i, i + 4);
    }


    public AttributedStr() {
        setBackground(Color.white);

        Font font = getFont("A.ttf");
        if (font != null) {
            font = font.deriveFont(PLAIN, 70);
        } else {
            font = new Font("serif", PLAIN, 50);
        }
        int index = text.indexOf("A") + 1;
        as.addAttribute(FONT, font, 0, index);
        as.addAttribute(FOREGROUND, white, 0, index);

        font = new Font("dialog", PLAIN, 40);
        int size = getFontMetrics(font).getHeight();
        BufferedImage bi =
                new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D big = bi.createGraphics();
        big.drawImage(getImage("snooze.gif"), 0, 0, size, size, null);
        ImageGraphicAttribute iga =
                new ImageGraphicAttribute(bi, GraphicAttribute.TOP_ALIGNMENT);
        as.addAttribute(CHAR_REPLACEMENT, iga, text.length() - 1, text.length());

        aci = as.getIterator();
    }


    @Override
    public void render(int w, int h, Graphics2D g2) {

        float x = 5, y = 0;
        FontRenderContext frc = g2.getFontRenderContext();
        LineBreakMeasurer lbm = new LineBreakMeasurer(aci, frc);

        g2.setPaint(new GradientPaint(0, h, blue, w, 0, black));
        g2.fillRect(0, 0, w, h);

        g2.setColor(white);
        String s = "AttributedString LineBreakMeasurer";
        Font font = new Font("serif", PLAIN, 12);
        TextLayout tl = new TextLayout(s, font, frc);

        tl.draw(g2, 5, y += (float) tl.getBounds().getHeight());

        g2.setColor(yellow);

        while (y < h - tl.getAscent()) {
            lbm.setPosition(0);
            while (lbm.getPosition() < text.length()) {
                tl = lbm.nextLayout(w - x);
                if (!tl.isLeftToRight()) {
                    x = w - tl.getAdvance();
                }
                tl.draw(g2, x, y += tl.getAscent());
                y += tl.getDescent() + tl.getLeading();
            }
        }
    }


    public static void main(String s[]) {
        createDemoFrame(new AttributedStr());
    }
}
