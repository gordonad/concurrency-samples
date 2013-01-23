package concurrency.ch09_swingworker_and_fork_join.solution_9_2;

import javax.swing.*;

/**
 * DO NOT CHANGE.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PuzzleFrame frame = new PuzzleFrame();
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
