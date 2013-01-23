package concurrency.ch06_task_execution.exercise_6_1;

import concurrency.math.Factorizer;
import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import static junit.framework.Assert.assertEquals;

/**
 * DO NOT CHANGE.
 */
public class SerialFactorizer {
    private static final int NUMBERS_TO_CHECK = 20_000;

    @Test
    public void testSerialFactorizing() throws InterruptedException {
        long totalTime = System.currentTimeMillis();
        final long start = (1 << 19) - 1; // known Mersenne prime
        int primes = 0;
        for (int i = 0; i < NUMBERS_TO_CHECK; i++) {
            long[] factors = Factorizer.factor(start + i);
            if (factors.length == 1) {
                primes++;
            }
        }
        System.out.println("primes = " + primes);
        assertEquals(1514, primes);
        ThreadMXBean tb = ManagementFactory.getThreadMXBean();
        System.out.println("PeakThreadCount = " + tb.getPeakThreadCount());
        totalTime = System.currentTimeMillis() - totalTime;
        System.out.println("totalTime = " + totalTime);
    }
}