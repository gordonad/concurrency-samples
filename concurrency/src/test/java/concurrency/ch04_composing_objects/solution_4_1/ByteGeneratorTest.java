package concurrency.ch04_composing_objects.solution_4_1;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.Assert.assertEquals;

/**
 * DO NOT CHANGE.
 */
public class ByteGeneratorTest {
    private static final int REPEATS = 10_000;

    @Test
    public void testGenerate() {
        ByteGenerator g = new ByteGenerator();
        for (int n = 0; n < 10; n++) {
            for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
                assertEquals(i, g.nextValue());
            }
        }
    }

    @Test
    public void testGenerateMultiThreaded() throws InterruptedException {
        final ByteGenerator g = new ByteGenerator();
        final AtomicInteger[] counters = new AtomicInteger[Byte.MAX_VALUE - Byte.MIN_VALUE + 1];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new AtomicInteger(0);
        }
        Thread[] threads = new Thread[100];
        final CountDownLatch latch = new CountDownLatch(threads.length);
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int repeats = 0; repeats < REPEATS; repeats++) {
                            for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
                                byte value = g.nextValue();
                                counters[value - Byte.MIN_VALUE].incrementAndGet();
                            }
                        }
                    } finally {
                        latch.countDown();
                    }
                }
            }, "generator-client-" + i);
            threads[i].setDaemon(true);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        latch.await();
        for (int i = 0; i < counters.length; i++) {
            System.out.println("value #" + (i + Byte.MIN_VALUE) + ": " + counters[i].get());
        }
        //print out the number of hits for each value
        for (int i = 0; i < counters.length; i++) {
            assertEquals("value #" + (i + Byte.MIN_VALUE), threads.length * REPEATS, counters[i].get());
        }
    }
}