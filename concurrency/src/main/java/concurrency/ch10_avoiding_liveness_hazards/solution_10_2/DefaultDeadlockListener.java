package concurrency.ch10_avoiding_liveness_hazards.solution_10_2;

public class DefaultDeadlockListener implements ThreadDeadlockDetector.Listener {
    @Override
    public void deadlockDetected(Thread[] threads) {
        System.err.println("Deadlocked Threads:");
        System.err.println("-------------------");
        for (Thread thread : threads) {
            System.err.println(thread);
            for (StackTraceElement ste : thread.getStackTrace()) {
                System.err.println("\t" + ste);
            }
        }
    }
}
