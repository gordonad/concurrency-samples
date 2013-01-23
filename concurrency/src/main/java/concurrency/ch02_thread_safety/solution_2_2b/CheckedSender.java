package concurrency.ch02_thread_safety.solution_2_2b;

import java.io.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

/**
 * Instead of locking the entire sendMessage() method, we only lock the actual
 * sending over the stream.  The object is first converted to a byte[] and then
 * is sent using an ordinary DataOutputStream.
 */
public class CheckedSender {
    private final DataOutputStream out;

    public CheckedSender(OutputStream out) throws IOException {
        this.out = new DataOutputStream(out);
    }

    public void sendMessage(Object msg) throws IOException {
        final Checksum checksum = new Adler32();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(
                new CheckedOutputStream(baos, checksum));
        oout.writeUnshared(msg);
        oout.writeLong(checksum.getValue());
        oout.close();
        byte[] data = baos.toByteArray();
        synchronized (this) {
            out.writeInt(data.length);
            out.write(data);
        }
    }
}