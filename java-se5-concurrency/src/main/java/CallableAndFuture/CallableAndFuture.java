package CallableAndFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demonstration of the use of a Future to return the results from a
 * Callable.  This shows the simplicity of synchronising two concurrent
 * threads
 */
public class CallableAndFuture {
    /**
     * Constructor
     */
    public CallableAndFuture() {
    }

    /**
     * Run the test
     */
    public void runTest() {
    /*  Use the Executors utility method to get an ExecutorService for a
     *  separate thread of execution
     */
        ExecutorService threadExecutor = Executors.newSingleThreadExecutor();
        System.out.println("Starting test in first thread");
        
        /*  Now attempt to run a task in the second thread  */
        try {
            Future<String> future = threadExecutor.submit(new CallableExample());
      /*  Set this sleep to either 100 0r 1000 to see the synchronisation
       *  effects
       */
            Thread.sleep(1000);
            System.out.println("First thread work complete.  Asking future for result");
            String result = future.get();
            System.out.println("Result from Future is " + result);
        } catch (Exception e) {
            System.out.println("Got an exception executing the test");
            System.out.println(e.getMessage());
        }
        
    /*  Shutdown the second thread so the program terminates gracefully
     */
        threadExecutor.shutdown();
    }

    /**
     * Main entry point
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        CallableAndFuture fe = new CallableAndFuture();
        fe.runTest();
    }
}