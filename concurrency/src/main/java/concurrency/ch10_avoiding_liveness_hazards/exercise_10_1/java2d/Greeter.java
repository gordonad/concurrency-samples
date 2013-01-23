package concurrency.ch10_avoiding_liveness_hazards.exercise_10_1.java2d;

import java.awt.*;

/**
 * Class that updates the global label to greet the user.
 */
public class Greeter {
    public static final Label label = new Label("<empty>");

    static {
        new Greeter();
    }

    private static void hello() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                label.setText("Good morning!");
            }
        });
    }

    private Greeter() {
        Thread t = new Thread() {
            @Override
            public void run() {
                hello();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void init() {
    }
}