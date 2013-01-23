package concurrency.ch07_cancellation_and_shutdown.exercise_7_1;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.*;

import static junit.framework.Assert.assertTrue;

/**
 * DO NOT CHANGE.
 */
public class FactorizerTest {
    @Test
    public void testIntPrimeFactoring() throws InterruptedException {
        check(2, 2, 11);
        check(2);
        check(3);
        check(7, 11);
        check(101);
        check(127);
    }

    @Test
    public void testLargeNumberPrimeFactoring() throws InterruptedException {
        check(173, 739, 967, 4_870_693);
        check(7, 29, 631, 809, 14_481_869);
        check(223, 647, 10651, 30_735_589);
    }

    @Test
    public void testCancelation() throws InterruptedException {
        Callable<long[]> factorLargeNumber = new Callable<long[]>() {
            @Override
            public long[] call() throws Exception {
                Factorizer factorizer = new Factorizer();
                return factorizer.factor(Integer.MAX_VALUE);
            }

        };
        ExecutorService pool = Executors.newCachedThreadPool();
        long time = System.currentTimeMillis();
        Future<long[]> future = pool.submit(factorLargeNumber);
        Thread.sleep(500);
        pool.shutdownNow(); // this will also interrupt the factoring thread
        assertTrue("Factorizer did not shut down within 500ms",
                pool.awaitTermination(500, TimeUnit.MILLISECONDS));
        try {
            long[] result = future.get();
            System.out.println("Result is " + Arrays.toString(result));
        } catch (ExecutionException e) {
            System.out.println("Received a " + e.getCause());
        }
    }

    private void check(long... nums) throws InterruptedException {
        long number = 1;
        for (long i : nums) {
            number *= i;
        }
        Factorizer factorizer = new Factorizer();
        long[] result = factorizer.factor(number);
        assertTrue(Arrays.equals(nums, result));
    }

}
