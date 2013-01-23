package concurrency.ch07_cancellation_and_shutdown.solution_7_2_broken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// try with 31, 37, 41, 49, 61
public class MersennePrimeFinderFrame extends JFrame {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private Future<Void> future;

    private final Factorizer factorizer = new Factorizer();

    private final MersennePrimeFinderPanel panel = new MersennePrimeFinderPanel();
    private final JButton factorizeButton = new JButton("Factorize");
    private final JButton cancelButton = new JButton("Cancel");
    private volatile Thread thread;

    public MersennePrimeFinderFrame() {
        super("Mersenne Prime Finder");
        add(panel);
        JPanel buttons = new JPanel();
        buttons.add(factorizeButton);
        buttons.add(cancelButton);
        add(buttons, BorderLayout.SOUTH);
        cancelButton.setEnabled(false);

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
        final long exponent = panel.getExponent();

        factorizeButton.setEnabled(false);
        cancelButton.setEnabled(true);
        panel.clear();

        thread = new Thread("Factorizer thread") {
            @Override
            public void run() {
                try {
                    long number = (1L << exponent) - 1;
                    long start = System.currentTimeMillis();
                    final long[] factors = factorizer.factor(number);
                    final long time = System.currentTimeMillis() - start;
                    panel.setResult(factors, time);
                    panel.clear();
                } finally {
                    factorizeButton.setEnabled(true);
                    cancelButton.setEnabled(false);
                }
            }
        };
        thread.start();
    }

    private void cancel() {
        thread.stop();
        thread = null;
    }
}
