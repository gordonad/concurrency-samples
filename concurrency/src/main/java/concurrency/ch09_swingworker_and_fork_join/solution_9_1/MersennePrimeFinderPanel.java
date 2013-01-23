package concurrency.ch09_swingworker_and_fork_join.solution_9_1;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * DO NOT CHANGE.
 */
public class MersennePrimeFinderPanel extends JPanel {
    public MersennePrimeFinderPanel() {
        setLayout(new GridBagLayout());
        setEnabled(false);
        JLabel jlabel = new JLabel();
        jlabel.setText("2");
        Font font;
        jlabel.setFont(new Font((font = jlabel.getFont()).getName(), font.getStyle(), 28));
        GridBagConstraints gridbagconstraints = new GridBagConstraints();
        gridbagconstraints.gridx = 0;
        gridbagconstraints.gridy = 0;
        gridbagconstraints.gridheight = 2;
        gridbagconstraints.anchor = 17;
        add(jlabel, gridbagconstraints);
        JLabel jlabel1 = new JLabel();
        jlabel1.setText(" - 1 = ");
        Font font1;
        jlabel1.setFont(new Font((font1 = jlabel1.getFont()).getName(), font1.getStyle(), 28));
        GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
        gridbagconstraints1.gridx = 2;
        gridbagconstraints1.gridy = 0;
        gridbagconstraints1.gridheight = 2;
        gridbagconstraints1.anchor = 17;
        add(jlabel1, gridbagconstraints1);
        exponent.setColumns(4);
        GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
        gridbagconstraints2.gridx = 1;
        gridbagconstraints2.gridy = 0;
        gridbagconstraints2.anchor = 17;
        gridbagconstraints2.fill = 2;
        add(exponent, gridbagconstraints2);
        JLabel jlabel2 = new JLabel();
        jlabel2.setText("     ");
        GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
        gridbagconstraints3.gridx = 1;
        gridbagconstraints3.gridy = 1;
        gridbagconstraints3.anchor = 17;
        gridbagconstraints3.ipady = 20;
        add(jlabel2, gridbagconstraints3);
        result.setText("...");
        Font font2;
        result.setFont(new Font((font2 = result.getFont()).getName(), font2.getStyle(), 28));
        GridBagConstraints gridbagconstraints4 = new GridBagConstraints();
        gridbagconstraints4.gridx = 3;
        gridbagconstraints4.gridy = 0;
        gridbagconstraints4.gridheight = 2;
        gridbagconstraints4.anchor = 17;
        add(result, gridbagconstraints4);
        JPanel jpanel1 = new JPanel();
        GridBagConstraints gridbagconstraints5 = new GridBagConstraints();
        gridbagconstraints5.gridx = 4;
        gridbagconstraints5.gridy = 0;
        gridbagconstraints5.fill = 2;
        add(jpanel1, gridbagconstraints5);
        JPanel jpanel2 = new JPanel();
        GridBagConstraints gridbagconstraints6 = new GridBagConstraints();
        gridbagconstraints6.gridx = 1;
        gridbagconstraints6.gridy = 2;
        gridbagconstraints6.fill = 3;
        add(jpanel2, gridbagconstraints6);
    }

    private final JLabel result = new JLabel();
    private final JTextField exponent = new JTextField();

    public int getExponent() {
        int exp = Integer.parseInt(exponent.getText());
        if (exp < 1 || exp > 63)
            throw new IllegalStateException("Illegal value for exponent, must be between 1 and 63");
        return exp;
    }

    public void setResult(long[] factors, long time) {
        result.setText(Arrays.toString(factors) + " (" + time + "ms)");
    }

    public void clear() {
        result.setText("...");
    }
}
