package concurrency.ch06_task_execution.exercise_6_1;

import concurrency.math.Factorizer;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.Assert.assertEquals;

/**
 * Instead of constructing a separate thread for each number, we want to rather
 * use a thread pool.  This should hopefully reduce the latency for factorizing
 * one prime, as we do not need to include the start-up time of a thread.
 */
public class ParallelFactorizer {
    private static final int NUMBERS_TO_CHECK = 20_000;

    private int factorizeInParallel(long start, int numbersToCheck)
            throws InterruptedException {
        final AtomicInteger primes = new AtomicInteger();

        // Change this block of code only:
        final AtomicLong next = new AtomicLong(start);
        Thread[] threads = new Thread[numbersToCheck];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    long number = next.getAndIncrement();
                    long[] factors = Factorizer.factor(number);
                    if (factors.length == 1) {
                        primes.incrementAndGet();
                    }
                }
            };
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        return primes.get();
    }

    /**
     * DO NOT CHANGE.
     */
    @Test
    public void testParallelFactorizing() throws InterruptedException {
        long totalTime = System.currentTimeMillis();
        long start = (1 << 19) - 1; // known Mersenne prime
        int primes = factorizeInParallel(start, NUMBERS_TO_CHECK);
        System.out.println("primes = " + primes);
        assertEquals(1514, primes);
        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
        System.out.println("PeakThreadCount = " + tb.getPeakThreadCount());
        totalTime = System.currentTimeMillis() - totalTime;
        System.out.println("totalTime = " + totalTime);
    }
}