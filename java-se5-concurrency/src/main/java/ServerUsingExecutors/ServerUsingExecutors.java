package ServerUsingExecutors;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerUsingExecutors {
    private final static int MAX_THREADS = 3;

    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public ServerUsingExecutors(int port, int poolSize) throws IOException {
        /*  Create a new ServerSocket to listen for incoming connections  */
        serverSocket = new ServerSocket(port);
        
        /*  Use the Exectors factory method to get a ThreadPool  */
        pool = Executors.newFixedThreadPool(MAX_THREADS);

    }

    /**
     * Service requests
     */
    public void serviceRequests() {
        int count = 1;
        int qLength = 0;

        try {
            for (; ; ) {
                pool.execute(new ConnectionHandler(serverSocket.accept(), count++));
            }
        } catch (IOException ioe) {
            System.out.println("IO Error in ConnectionHandler: " + ioe.getMessage());
            pool.shutdown();
        }

    }

    public static void main(String[] args) {
        System.out.println("Listening for connections...");
        ServerUsingExecutors ce = null;

        try {
            ce = new ServerUsingExecutors(8100, 4);
            ce.serviceRequests();
        } catch (IOException ioe) {
            System.out.println("IO Error creating listener: " + ioe.getMessage());
        }
    }
}