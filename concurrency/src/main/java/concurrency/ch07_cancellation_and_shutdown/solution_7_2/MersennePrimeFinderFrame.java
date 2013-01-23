package concurrency.ch07_cancellation_and_shutdown.solution_7_2;

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
 * In our solution, we submit the factorization call to a separate thread in a
 * thread pool.  If it gets canceled, we also cancel the executor thread, which
 * interrupts the thread.  We have to take care to only access the GUI classes
 * from within the GUI thread.  Swing is not thread-safe.  We do that with the
 * SwingUtilities.invokeLater() calls.  Another solution, which we will see in
 * chapter 9, is to use the SwingWorker.
 * Note that "future" can be non-volatile and does not need any synchronization,
 * as it is only ever accessed from the GUI thread.
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
