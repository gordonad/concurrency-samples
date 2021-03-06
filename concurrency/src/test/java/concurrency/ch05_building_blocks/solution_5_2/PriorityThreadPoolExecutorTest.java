package concurrency.ch05_building_blocks.solution_5_2;

import concurrency.ch05_building_blocks.exercise_5_2.Priority;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

/**
 * DO NOT CHANGE.
 */
public class PriorityThreadPoolExecutorTest {
    private static volatile CountDownLatch latch;
    private static final AtomicInteger nextSequence = new AtomicInteger(0);

    private static void checkExpectedNumber(int expectedSequence) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(expectedSequence);
        if (expectedSequence != nextSequence.getAndIncrement()) {
            System.err.println("Job " + expectedSequence + " out of sequence");
        } else {
            latch.countDown();
        }
    }

    private static class MyRunnable implements Runnable {
        private final int expectedSequence;

        private MyRunnable(int expectedSequence) {
            this.expectedSequence = expectedSequence;
        }

        @Override
        public void run() {
            checkExpectedNumber(expectedSequence);
        }
    }

    private static class MyCallable implements Callable<Void> {
        private final int expectedSequence;

        private MyCallable(int expectedSequence) {
            this.expectedSequence = expectedSequence;
        }

        @Override
        public Void call() throws Exception {
            checkExpectedNumber(expectedSequence);
            return null;
        }
    }

    @Test
    public void testOrderOfElements() throws InterruptedException {
        nextSequence.set(0);
        latch = new CountDownLatch(20);
        PriorityThreadPoolExecutor pool = new PriorityThreadPoolExecutor(1);
        // submitting normal priority jobs
        pool.execute(new MyRunnable(0), Priority.NORMAL);
        Thread.sleep(50);

        pool.execute(new MyRunnable(6), Priority.NORMAL);
        pool.execute(new MyRunnable(7), Priority.NORMAL);
        pool.submit(new MyCallable(8), Priority.NORMAL);
        pool.execute(new MyRunnable(9), Priority.NORMAL);
        pool.execute(new MyRunnable(10), Priority.NORMAL);

        pool.execute(new MyRunnable(15), Priority.LOW);
        pool.submit(new MyCallable(16), Priority.LOW);
        pool.execute(new MyRunnable(17), Priority.LOW);
        pool.execute(new MyRunnable(18), Priority.LOW);

        pool.execute(new MyRunnable(1), Priority.HIGH);

        pool.execute(new MyRunnable(19), Priority.LOW);

        pool.execute(new MyRunnable(11), Priority.NORMAL);
        pool.submit(new MyCallable(12), Priority.NORMAL);
        pool.execute(new MyRunnable(13), Priority.NORMAL);
        pool.execute(new MyRunnable(14), Priority.NORMAL);

        pool.execute(new MyRunnable(2), Priority.HIGH);
        pool.submit(new MyRunnable(3), null, Priority.HIGH);
        pool.execute(new MyRunnable(4), Priority.HIGH);
        pool.execute(new MyRunnable(5), Priority.HIGH);

        pool.shutdown();

        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void testOrderOfElements2() throws InterruptedException {
        nextSequence.set(0);
        latch = new CountDownLatch(20);
        PriorityThreadPoolExecutor pool = new PriorityThreadPoolExecutor(1);
        // submitting normal priority jobs
        pool.execute(new MyRunnable(0), Priority.NORMAL);
        Thread.sleep(50);

        pool.execute(new MyRunnable(6), Priority.NORMAL);
        pool.execute(new MyRunnable(7), Priority.NORMAL);
        pool.execute(new MyRunnable(8), Priority.NORMAL);
        pool.execute(new MyRunnable(9), Priority.NORMAL);
        pool.execute(new MyRunnable(10), Priority.NORMAL);

        pool.execute(new MyRunnable(15), Priority.LOW);
        pool.execute(new MyRunnable(16), Priority.LOW);
        pool.execute(new MyRunnable(17), Priority.LOW);
        pool.execute(new MyRunnable(18), Priority.LOW);

        pool.execute(new MyRunnable(1), Priority.HIGH);

        pool.execute(new MyRunnable(19), Priority.LOW);

        pool.execute(new MyRunnable(11), Priority.NORMAL);
        pool.execute(new MyRunnable(12), Priority.NORMAL);
        pool.execute(new MyRunnable(13), Priority.NORMAL);
        pool.execute(new MyRunnable(14), Priority.NORMAL);

        pool.execute(new MyRunnable(2), Priority.HIGH);
        pool.execute(new MyRunnable(3), Priority.HIGH);
        pool.execute(new MyRunnable(4), Priority.HIGH);
        pool.execute(new MyRunnable(5), Priority.HIGH);

        pool.shutdown();

        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }
}