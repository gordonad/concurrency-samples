package concurrency.ch07_cancellation_and_shutdown.exercise_7_2;

import concurrency.ch07_cancellation_and_shutdown.exercise_7_1.Factorizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TODO: Implement the "cancel" functionality, allowing a user to cancel the
 * TODO: search if it takes too long.
 * You can try running this with the following numbers: 31, 37, 41, 49, 61
 */
public class MersennePrimeFinderFrame extends JFrame {
    private final Factorizer factorizer = new Factorizer();
    private final MersennePrimeFinderPanel panel =
            new MersennePrimeFinderPanel();

    public MersennePrimeFinderFrame() {
        super("Mersenne Prime Finder");
        add(panel);
        JPanel buttons = new JPanel();
        JButton factorizeButton = new JButton("Factorize");
        JButton cancelButton = new JButton("Cancel");
        buttons.add(factorizeButton);
        buttons.add(cancelButton);
        add(buttons, BorderLayout.SOUTH);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });
        factorizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factorize();
            }
        });
    }

    private void factorize() {
        try {
            long exponent = panel.getExponent();
            long number = (1L << exponent) - 1;
            long time = System.currentTimeMillis();
            long[] factors = factorizer.factor(number);
            time = System.currentTimeMillis() - time;
            panel.setResult(factors, time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void cancel() {
        throw new UnsupportedOperationException("todo");
    }
}
