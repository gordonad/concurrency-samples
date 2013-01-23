package concurrency.ch09_swingworker_and_fork_join.solution_9_1;

import concurrency.ch07_cancellation_and_shutdown.solution_7_1.Factorizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class now uses the SwingWorker to do the factorization, allowing us to
 * cancel() the factorization.  The background work needs to go into the
 * doInBackground() method inside the SwingWorker.  That is the only code
 * that will be called by a non-Swing event dispatch thread.  Thus we do not
 * need to use the SwingUtilities.invoke*() methods anywhere else.  Try the
 * program with values 31, 37, 41, 49, 61.
 */
public class MersennePrimeFinderFrame extends JFrame {
    private final Factorizer factorizer = new Factorizer();

    private final MersennePrimeFinderPanel panel = new MersennePrimeFinderPanel();
    private final JButton factorizeButton = new JButton("Factorize");
    private final JButton cancelButton = new JButton("Cancel");

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

    private SwingWorker<long[], Object> worker = null;

    private void factorize() {
        final long exponent = panel.getExponent();

        factorizeButton.setEnabled(false);
        cancelButton.setEnabled(true);
        panel.clear();

        worker = new SwingWorker() {
            long time;
            long[] factors;

            @Override
            protected long[] doInBackground() throws InterruptedException {
                long number = (1L << exponent) - 1;
                long start = System.currentTimeMillis();
                factors = factorizer.factor(number);
                time = System.currentTimeMillis() - start;
                return factors;
            }

            @Override
            protected void done() {
                if (factors != null) {
                    panel.setResult(factors, time);
                } else {
                    panel.clear();
                }
                factorizeButton.setEnabled(true);
                cancelButton.setEnabled(false);
                worker = null;
            }
        };
        worker.execute();
    }

    private void cancel() {
        worker.cancel(true);
    }
}
