package concurrency.ch10_avoiding_liveness_hazards.solution_10_2;

import concurrency.ch10_avoiding_liveness_hazards.exercise_10_2.PrinterClass;

public class PrinterClassAutomaticDetectionTest {
    public static void main(String[] args) {
        ThreadDeadlockDetector detector = new ThreadDeadlockDetector(500);
        detector.addListener(new DefaultDeadlockListener() {
            @Override
            public void deadlockDetected(Thread[] threads) {
                super.deadlockDetected(threads);
                System.exit(1);
            }
        });

        final PrinterClass pc = new PrinterClass();
        new Thread() {
            {
                start();
            }

            @Override
            public void run() {
                for (int i = 0; i < 10_000_000; i++) {
                    pc.print("testing");
                }
            }
        };
        new Thread() {
            {
                start();
            }

            @Override
            public void run() {
                for (int i = 0; i < 10_000_000; i++) {
                    pc.setPrintingEnabled(false);
                }
            }
        };
    }
}