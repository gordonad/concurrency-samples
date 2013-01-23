package ServerUsingThreadPoolExecutor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerUsingThreadPoolExecutor {

    private final static int MAX_THREADS = 2;

    private final ServerSocket serverSocket;
    private final ThreadPoolExecutor pool;
    private final ArrayBlockingQueue<Runnable> workQueue;

    /**
     * Constructor
     */
    public ServerUsingThreadPoolExecutor(int port, int poolSize) throws IOException {
        /*  Create a new ServerSocket to listen for incoming connections  */
        serverSocket = new ServerSocket(port);
        
       /*  In order to create a pool of threads, we must first have a queue
        *  that will be used to hold the work tasks before they are executed
        *  For this example we use a ArrayBlockingQueue that can hold the
        *  same number of tasks as we have maximum threads
        */
        workQueue = new ArrayBlockingQueue<Runnable>(MAX_THREADS);
        
       /*  Now create a ThreadPool.  The initial and maximum number of
        *  threads are the same in this example.  Please note that the
        *  MAX_THREADS is set to 2.
        */
        pool = new ThreadPoolExecutor(MAX_THREADS, MAX_THREADS,
                60, TimeUnit.SECONDS, workQueue);
    }

    /**
     * Service requests
     */
    public void serviceRequests() {
        int count = 1;
        int qLength = 0;

        try {
            for (; ; ) {
                if ((qLength = workQueue.size()) > 0)
                    System.out.println("Queue length is " + qLength);

                pool.execute(new ConnectionHandler(serverSocket.accept(), count++));
            }
        } catch (IOException ioe) {
            System.out.println("IO Error in ConnectionHandler: " + ioe.getMessage());
            pool.shutdown();
        }
    }

    /**
     * Main entry point
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Listening for connections...");
        ServerUsingThreadPoolExecutor ce = null;

        try {
            ce = new ServerUsingThreadPoolExecutor(8100, 4);
            
            /*
             * Serve incoming connection request until interrupted.
             */
            ce.serviceRequests();
        } catch (IOException ioe) {
            System.out.println("IO Error creating listener: " + ioe.getMessage());
        }
    }
}