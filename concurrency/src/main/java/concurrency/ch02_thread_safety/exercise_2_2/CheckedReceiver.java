package concurrency.ch02_thread_safety.exercise_2_2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * TODO: Fix the concurrency bug
 * Modify this class to allow many threads to call receiveMessage() at the same
 * time without any data corruption occuring.
 */
public class CheckedReceiver {
    private final Checksum checksum = new Adler32();

    private final ObjectInputStream in;

    public CheckedReceiver(InputStream in) throws IOException {
        this.in = new ObjectInputStream(
                new CheckedInputStream(in, checksum));
    }

    public Object receiveMessage() throws IOException, ClassNotFoundException {
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