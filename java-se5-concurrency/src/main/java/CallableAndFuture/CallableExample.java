package CallableAndFuture;

import java.util.concurrent.Callable;

/**
 * A simple implementation of the Callable interface that can return a value
 * to the thread that started it
 */
public class CallableExample implements Callable<String> {
    /**
     * The entry point called when this object is invoked as a new thread
     * of execution
     *
     * @returns A String as a simple result
     */
    @Override
    public String call() {
        System.out.println("Starting call() method in second thread");

        try {
            Thread.sleep(500);
        } catch (InterruptedException ie) {
            // Ignore
        }

        System.out.println("Completed call() method in second thread");
        return "Finished";
    }
}