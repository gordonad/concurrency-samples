package ConnectionClient;

import java.io.OutputStream;
import java.net.Socket;

/**
 * ConnectionClient pass characters from the keyboard to a socket.  Simplified version of
 * telnet
 */
public class ConnectionClient {

    /**
     * Main entry point
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        String host = null;
        int port = 0;

        if (args.length < 2) {
            host = "127.0.0.1";
            port = 8100;
        }

        OutputStream os = null;

        try {
            // The Socket() call returns only when the connection
            // request from this client is accepted by the server.
            Socket s = new Socket(host, port);
            os = s.getOutputStream();
            System.out.println("Connection established to server. Type characters and press <ENTER> to send");
            System.out.println("Type EXIT and press <RETURN> to exit");
            
            /*  Read data from the standard input and send it to the remote socket  */
            while (true) {
                byte[] inData = new byte[100];

                System.in.read(inData);
                String inString = new String(inData);

                if (inString.substring(0, 4).compareTo("EXIT") == 0)
                    System.exit(1);

                os.write(inData);
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to remote host: " +
                    e.getMessage());
        }
    }
}