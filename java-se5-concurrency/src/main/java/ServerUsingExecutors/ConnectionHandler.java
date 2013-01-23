package ServerUsingExecutors;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private final Socket socket;
    private final int connectionID;

    /**
     * Constructor
     *
     * @param socket The socket on which incoming data will arrive
     */
    public ConnectionHandler(Socket socket, int connectionID) {
        this.socket = socket;
        this.connectionID = connectionID;
    }

    /**
     * run method to do the work of the handler
     */
    @Override
    public void run() {
        System.out.println("Connection " + connectionID + ", started");

        try {
            InputStream is = socket.getInputStream();

            //  Loop to do something with the socket here
            while (true) {
                byte[] inData = new byte[100];
                
                /*  If the number of bytes read is less than zero then the connection
                 *  has been terminated so we end the thread
                 */
                if (is.read(inData) < 0)
                    break;

                System.out.println("[" + connectionID + "]: " + new String(inData));
            }
        } catch (IOException ioe) {
            // Ignore
        }

        System.out.println("Connection " + connectionID + ", ended");
    }
}