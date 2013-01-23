package concurrency.ch06_task_execution.solution_6_1b;

import concurrency.math.Factorizer;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.Assert.assertEquals;

/**
 * In this solution, we go one step further by letting each task solve several
 * numbers.
 */
public class ParallelFactorizer {
    private static final int NUMBERS_TO_CHECK = 20_000;
    private static final int NUMBERS_PER_TASK = 400;

    private int factorizeInParallel(long start, int numbersToCheck)
            throws InterruptedException {
        final AtomicInteger primes = new AtomicInteger();

        // Change this block of code only:
        final AtomicLong next = new AtomicLong(start);
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2);
        for (int i = 0; i < NUMBERS_TO_CHECK / NUMBERS_PER_TASK; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < NUMBERS_PER_TASK; i++) {
                        long number = next.getAndIncrement();
                        long[] factors = Factorizer.factor(number);
                        if (factors.length == 1) {
                            primes.incrementAndGet();
                        }
                    }
                }
            });
        }
        pool.shutdown();
        while (!pool.awaitTermination(1, TimeUnit.SECONDS)) ;

        return primes.get();
    }

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