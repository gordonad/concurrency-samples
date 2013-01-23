package concurrency.ch02_thread_safety.solution_2_2;

import concurrency.ch02_thread_safety.exercise_2_2.Marker;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

/**
 * The first solution is the simplest - we make the sendMessage() synchronized.
 * It might be safer to synchronize on the OutputStream though, as several
 * CheckedSenders might be sharing the same OutputStream.
 */
public class CheckedSender {
    private final Checksum checksum = new Adler32();

    private final ObjectOutputStream out;
    private static final Object MARKER = new Marker();

    public CheckedSender(OutputStream out) throws IOException {
        this.out = new ObjectOutputStream(
                new CheckedOutputStream(out, checksum));
    }

    public synchronized void sendMessage(Object msg) throws IOException {
        out.writeObject(MARKER);
        checksum.reset();
        out.writeObject(msg);
        out.writeLong(checksum.getValue());
        out.reset();
        out.flush();
    }
}
