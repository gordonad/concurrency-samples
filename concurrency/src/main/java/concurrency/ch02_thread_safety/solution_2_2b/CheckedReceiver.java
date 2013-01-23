package concurrency.ch02_thread_safety.solution_2_2b;

import java.io.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/**
 * In this solution, we only synchronize the actual reading of the byte[].
 * The bytes are then converted to an object and the checksum is applied with
 * local variables to avoid having to do locking.
 */
public class CheckedReceiver {
    private final DataInputStream in;

    public CheckedReceiver(InputStream in) throws IOException {
        this.in = new DataInputStream(in);
    }

    public Object receiveMessage() throws IOException, ClassNotFoundException {
        byte[] data;
        synchronized (this) {
            int length = in.readInt();
            data = new byte[length];
            int readData = 0;
            while (readData != length) {
                int readResult = in.read(data, readData, length - readData);
                if (readResult == -1) throw new EOFException();
                readData += readResult;
            }
        }

        Checksum checksum = new Adler32();
        ByteArrayInputStream baos = new ByteArrayInputStream(data);
        ObjectInputStream oin = new ObjectInputStream(
                new CheckedInputStream(baos, checksum));
        Object msg = oin.readUnshared();
        long streamChecksum = checksum.getValue();
        long check = oin.readLong();
        if (check != streamChecksum) {
            throw new StreamCorruptedException("Checksum comparison failed");
        }
        return msg;
    }
}