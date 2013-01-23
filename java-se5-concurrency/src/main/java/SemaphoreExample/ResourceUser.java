package SemaphoreExample;

/**
 *
 *  Concurrency utilities (JSR-166) example
 **/

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Use resources from a pool and return them at random times
 */
public class ResourceUser implements Runnable {
    private ResourcePool pool;
    private int threadId;
    private int runCount;
    private long hold;
    private long pause;

    /**
     * Constructor
     *
     * @param pool     The pool to get the resource from
     * @param threadId The id number of this thread
     * @param runCount The number of times to run the test loop
     * @param hold     How long to hold the resource before releasing it
     * @param pause    How long to wait before trying to re-aquire the resource
     */
    public ResourceUser(ResourcePool pool, int threadId, int runCount,
                        long hold, long pause) {
        this.pool = pool;
        this.threadId = threadId;
        this.runCount = runCount;
        this.hold = hold;
        this.pause = pause;
    }

    /**
     * Request a resource from the pool and
     * return them after a random time period
     */
    @Override
    public void run() {
        for (int i = 0; i < runCount; i++) {
            try {
                /*  Get the resource from the pool  */
                System.out.println("[" + threadId + "] trying to get resource");
                Integer resource = pool.getResource();
                System.out.println("[" + threadId + "] aquired resource");

                Thread.sleep(hold);
                System.out.println("[" + threadId + "] releasing resource");
                pool.putResource(resource);
                Thread.sleep(pause);
            } catch (InterruptedException ie) {
                //  Silently ignore
            }
        }
    }

    /**
     * Main entry point
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        /*  Create a resource pool with two resources in it  */
        ResourcePool pool = new ResourcePool(2);
        
    /*  Use the Executors utility class to get a new FixedThreadPool.
     */
        ExecutorService threadExecutor = Executors.newFixedThreadPool(3);
        
    /*  Create three new resource users and start them using the previously
     *  initialised thread pool
     */
        ResourceUser r1 = new ResourceUser(pool, 1, 5, 400, 500);
        ResourceUser r2 = new ResourceUser(pool, 2, 5, 800, 500);
        ResourceUser r3 = new ResourceUser(pool, 3, 5, 1200, 500);
        threadExecutor.execute(r1);
        threadExecutor.execute(r2);
        threadExecutor.execute(r3);
        
        /*  Clean up the thread pool so the program terminates cleanly  */
        threadExecutor.shutdown();
    }
}