package concurrency.ch11_performance_and_scalability.exercise_11_1;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * Please investigate the performance differences in a simple put/take benchmark
 * for the following queues.
 */
public class QueueTest {
    private volatile static boolean timeIsUp;

    public static void main(String[] args) {
        for (int threads = 1; threads <= 1_000; threads *= 10) {
            test(threads, new SynchronousQueue<String>());
            test(threads, new ArrayBlockingQueue<String>(10_000));
            test(threads, new LinkedBlockingQueue<String>(10_000));
            test(threads, new LinkedTransferQueue<String>()); // Java 7
            test(threads, new PriorityBlockingQueue<String>(10_000));
        }
    }

    private static void test(int threads, final BlockingQueue<String> queue) {
        for (int i = 0; i < 10; i++) {
            Thread.interrupted();
            timeIsUp = false;

            final ExecutorService pool = Executors.newFixedThreadPool(threads);
            for (int j = 0; j < threads; j++) {
                pool.submit(new Runnable() {
                    @Override
                    public void run() {
                        while (!timeIsUp) {
                            try {
                                queue.take();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                    }
                });
            }
            Timer timer = new Timer(true);
            timer.schedule(new TimerTask() {
                private final Thread mainThread = Thread.currentThread();

                @Override
                public void run() {
                    timeIsUp = true;
                    mainThread.interrupt();
                    pool.shutdownNow();

                }
            }, 1_000);
            int jobsSubmitted = 0;
            while (!timeIsUp) {
                jobsSubmitted++;
                try {
                    queue.put("Heinz's Test");
                } catch (InterruptedException e) {
                    break;
                }
            }
            System.out.printf("%s:\t%d\t%,d%n",
                    queue.getClass().getSimpleName(), threads,
                    jobsSubmitted);
        }
    }
}
