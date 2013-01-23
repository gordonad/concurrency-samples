package concurrency.ch09_swingworker_and_fork_join.exercise_9_1;

import concurrency.ch07_cancellation_and_shutdown.solution_7_1.Factorizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TODO: Change this class to use SwingWorker instead of our own half-baked
 * TODO: threaded solution.  You should not need to use any of the
 * TODO: SwingUtilities.invoke*() methods in your modified class.  Try the
 * TODO: program with values 31, 37, 41, 49, 61.
 */
public class MersennePrimeFinderFrame extends JFrame {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private Future<Void> future;

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

    private void factorize() {
        final long exponent = panel.getExponent();

        factorizeButton.setEnabled(false);
        cancelButton.setEnabled(true);
        panel.clear();

        future = exec.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long number = (1L << exponent) - 1;
                long start = System.currentTimeMillis();
                try {
                    final long[] factors = factorizer.factor(number);
                    final long time = System.currentTimeMillis() - start;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.setResult(factors, time);
                        }
                    });
                } catch (InterruptedException ex) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            panel.clear();
                        }
                    });
                }
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        factorizeButton.setEnabled(true);
                        cancelButton.setEnabled(false);
                        future = null;
                    }
                });
                return null;
            }
        });
    }

    private void cancel() {
        Future<Void> future = this.future;
        future.cancel(true);
    }
}
