package concurrency.ch14_building_custom_synchronizers.solution_14_1;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import static org.junit.Assert.*;

/**
 * DO NOT CHANGE.
 */
public class FutureResultIterableTest {
    @Test
    public void testWithNoSubmits() throws InterruptedException {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        fri.shutdown();
        Thread.sleep(100);
        assertTrue(fri.isTerminated());
    }

    @Test
    public void testWithOneSubmit() throws ExecutionException, InterruptedException {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        fri.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("Trying call");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Hello World";
            }
        });

        Iterator<Future<String>> iterator = fri.iterator();
        assertTrue(iterator.hasNext());
        String str = iterator.next().get();
        System.out.println(str);
        assertEquals("Hello World", str);
        assertFalse(iterator.hasNext());
        fri.shutdown();
        Thread.sleep(500);
        assertTrue(fri.isTerminated());

        // after shutdown we can get the iterator again and it will return the
        // results as before
        iterator = fri.iterator();
        assertTrue(iterator.hasNext());
        str = iterator.next().get();
        System.out.println(str);
        assertEquals("Hello World", str);
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testWithTenSubmits() throws ExecutionException, InterruptedException {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            fri.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        Thread.sleep((long) (500 + Math.random() * 300));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "Hello World " + finalI;
                }
            });
        }

        long time = System.currentTimeMillis();
        int count = 0;
        for (Future<String> future : fri) {
            System.out.println(future.get());
            count++;
        }
        assertEquals(10, count);
        time = System.currentTimeMillis() - time;
        assertTrue(time < 1000);
        fri.shutdown();
    }

    @Test(expected = RejectedExecutionException.class)
    public void testSubmitAfterShutdown() {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        fri.shutdown();
        fri.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("Trying call");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Hello World";
            }
        });
    }

    @Test
    public void testWithEarlyShutdown() throws ExecutionException, InterruptedException {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            fri.submit(new Callable<String>() {
                @Override
                public String call() {
                    try {
                        Thread.sleep((long) (500 + Math.random() * 300));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return "Hello World " + finalI;
                }
            });
        }

        fri.shutdown();

        long time = System.currentTimeMillis();
        int count = 0;
        for (Future<String> future : fri) {
            System.out.println(future.get());
            count++;
        }
        assertEquals(10, count);
        time = System.currentTimeMillis() - time;
        assertTrue(time < 1000);
    }

    @Test
    public void testIndexOutOfBoundsProblem() {
        FutureResultIterable<String> fri = new FutureResultIterable<>();
        Thread.currentThread().interrupt();
        fri.submit(new Callable<String>() {
            @Override
            public String call() {
                System.out.println("Trying call");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Hello World";
            }
        });
        Thread.currentThread().interrupt();
        fri.iterator().next();
        assertTrue(Thread.interrupted());
    }
}
