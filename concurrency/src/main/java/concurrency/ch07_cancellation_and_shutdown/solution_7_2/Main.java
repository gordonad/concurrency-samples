package concurrency.ch07_cancellation_and_shutdown.solution_7_2;

import javax.swing.*;

/**
 * DO NOT CHANGE.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MersennePrimeFinderFrame frame = new MersennePrimeFinderFrame();
                frame.setSize(800, 200);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
