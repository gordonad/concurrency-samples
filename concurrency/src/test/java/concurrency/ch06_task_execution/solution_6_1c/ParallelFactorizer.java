package concurrency.ch06_task_execution.solution_6_1c;

import concurrency.math.Factorizer;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;

/**
 * For even better scalability, we eliminiate the "hot field" number and instead
 * assign ranges of numbers to each of the tasks.  This could make a difference
 * if the hot field becomes the bottleneck if we run this on a huge number of
 * cores.
 */
public class ParallelFactorizer {
    private static final int NUMBERS_TO_CHECK = 20_000;
    private static final int NUMBERS_PER_TASK = 400;

    private int factorizeInParallel(long start, int numbersToCheck)
            throws InterruptedException {
        final AtomicInteger primes = new AtomicInteger();

        // Change this block of code only:
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2);
        for (int i = 0; i < NUMBERS_TO_CHECK; i += NUMBERS_PER_TASK) {
            final long from = start + i;
            final long until = from + NUMBERS_PER_TASK;
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for (long number = from; number < until; number++) {
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