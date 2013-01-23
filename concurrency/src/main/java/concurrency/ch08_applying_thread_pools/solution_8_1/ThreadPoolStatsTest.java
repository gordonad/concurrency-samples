package concurrency.ch08_applying_thread_pools.solution_8_1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DO NOT CHANGE.
 */
public class ThreadPoolStatsTest {
    public static void main(String[] args) throws InterruptedException {
        ScheduledThreadPoolExecutor sched =
                (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
        ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        ThreadPoolExecutor cached = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        ThreadPoolStatsManager.register(cached, "cached");
        ThreadPoolStatsManager.register(fixed, "fixed");
        ThreadPoolStatsManager.register(sched, "sched");
        ThreadPoolStatsManager.register(
                (ThreadPoolExecutor) Executors.newCachedThreadPool(), "dummy");

        sched.scheduleAtFixedRate(new PoolSubmitter(cached, "cached"), 1, 1, TimeUnit.SECONDS);
        sched.scheduleAtFixedRate(new PoolSubmitter(fixed, "fixed"), 1, 1, TimeUnit.SECONDS);
        TimeUnit.MINUTES.sleep(5);
        fixed.shutdown();
        cached.shutdown();
        sched.shutdown();
    }

    private static class PoolSubmitter implements Runnable {
        private final AtomicInteger count = new AtomicInteger();
        private final ThreadPoolExecutor cached;
        private final String name;

        public PoolSubmitter(ThreadPoolExecutor cached, String name) {
            this.cached = cached;
            this.name = name;
        }

        @Override
        public void run() {
            cached.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println(name + count.incrementAndGet());
                }
            });
        }
    }
}