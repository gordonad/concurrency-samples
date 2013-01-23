package concurrency.ch02_thread_safety.solution_2_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * The first solution is the simplest - we make the receiveMessage()
 * synchronized.  It might be safer to synchronize on the InputStream though,
 * as several CheckedReceivers might be sharing it.
 */
public class CheckedReceiver {
    private final Checksum checksum = new Adler32();

    private final ObjectInputStream in;

    public CheckedReceiver(InputStream in) throws IOException {
        this.in = new ObjectInputStream(
                new CheckedInputStream(in, checksum));
    }

    public synchronized Object receiveMessage() throws IOException, ClassNotFoundException {
        in.readObject(); // marker object
        checksum.reset();
        Object msg = in.readObject();
        long streamChecksum = checksum.getValue();
        long check = in.readLong();
        if (check != streamChecksum) {
            throw new StreamCorruptedException("Checksum comparison failed");
        }
        return msg;
    }
}