package concurrency.ch12_testing_concurrent_programs.solution_12_1;

import concurrency.ch12_testing_concurrent_programs.exercise_12_1.HandoverQueue;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * The HandoverQueue is a half-baked queue implementation that needs to be
 * tested.
 * <p/>
 * First some correctness tests:
 * <p/>
 * A newly created HandoverQueue.isEmpty() should return true
 * If you "offer" some elements into you can "poll" them out again
 * Calling "peek" should return the first element without removing it
 * <p/>
 * Then some performance tests, using runConcurrently()
 * <p/>
 * We should run the code in several threads repeatedly calling
 * <p/>
 * q.offer(somevalue);
 * q.peek(); <-- should never be null
 * q.poll(); <-- should also never be null
 * <p/>
 * Run the performance test against LinkedList to verify that it picks up the
 * correctness problem with the list
 */
public class HandoverQueueTest {
    // single-threaded correctness tests
    //
    // If the handover queue is broken with one
    // thread, it for sure is broken with many!

    /**
     * Tests that a newly created HandoverQueue.isEmpty() return true.
     */
    @Test
    public void testBasicIsEmpty() {
        assertTrue(new HandoverQueue().isEmpty());
    }

    /**
     * Test that you can "offer" some elements into the
     * queue and then "poll" them out again.
     */
    @Test
    public void testOfferAndPoll() {
        HandoverQueue<Integer> q = new HandoverQueue<>();
        for (int i = 0; i < 10; i++) {
            assertTrue(q.offer(i));
        }
        assertEquals(10, q.size());
        for (int i = 0; i < 10; i++) {
            assertEquals(i, q.poll().intValue());
        }
    }

    /**
     * Test that "peek" returns the first element without
     * removing it.
     */
    @Test
    public void testPeek() {
        HandoverQueue<Integer> q = new HandoverQueue<>();
        for (int i = 0; i < 10; i++) {
            assertTrue(q.offer(i));
        }
        assertEquals(10, q.size());
        assertEquals(0, q.peek().intValue());
        assertEquals(0, q.peek().intValue());
        assertEquals(0, q.peek().intValue());
        assertEquals(10, q.size());
    }

    @Test
    public void testRandomConcurrentUpdates() throws InterruptedException, BrokenBarrierException {
        final HandoverQueue<Integer> q = new HandoverQueue<>();
        long timeToRun = runConcurrently(4,
                new Runnable() {
                    int[] numbers = generateRandomInts();

                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            for (int number : numbers) {
                                assertTrue(q.offer(number));
                                assertNotNull(q.peek());
                                assertNotNull(q.poll());
                            }
                        }
                    }
                });
        System.out.println("timeToRun = " + timeToRun);
        assertTrue(q.isEmpty());
    }

    private static final int NUMBER_OF_RANDOM_INTS = 500000;

    private int[] generateRandomInts() {
        int[] amounts = new int[NUMBER_OF_RANDOM_INTS];
        Random rand = new Random();
        for (int i = 0; i < amounts.length; i++) {
            amounts[i] = rand.nextInt(100);
        }
        return amounts;
    }

    /**
     * DO NOT CHANGE.
     */
    private long runConcurrently(int numberOfThreads,
                                 final Runnable task)
            throws InterruptedException, BrokenBarrierException {
        long time = System.currentTimeMillis();
        final CyclicBarrier startGun = new CyclicBarrier(numberOfThreads + 1);
        ExecutorService pool =
                Executors.newFixedThreadPool(numberOfThreads);
        Collection<Future<Void>> futures = new LinkedList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(pool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    startGun.await();
                    task.run();
                    return null;
                }
            }));
        }
        startGun.await();
        pool.shutdown();
        for (Future<Void> future : futures) {
            try {
                future.get(1, TimeUnit.MINUTES);
            } catch (ExecutionException e) {
                fail("Error in execution of thread: " + e);
            } catch (TimeoutException e) {
                fail("Job did not complete in time");
            }
        }
        assertTrue("Test did not complete in time",
                pool.awaitTermination(1, TimeUnit.MINUTES));
        time = System.currentTimeMillis() - time;
        return time;
    }
}