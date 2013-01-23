import java.util.concurrent.locks.*;

public class CubbyHole2 {

    private int contents;
    private boolean available = false;
    private Lock aLock = new ReentrantLock();
    private Condition condVar = aLock.newCondition();

    public int get(int who) {
        aLock.lock();
        try {
            while (available == false) {
                try {
                    condVar.await();
                } catch (InterruptedException e) { }
            }
            available = false;
            System.out.format("Consumer %d got: %d%n", who, contents);
            condVar.signalAll();
        } finally {
            aLock.unlock(); 
            return contents;
        }
    }

    public void put(int who, int value) {
        aLock.lock();
        try {
            while (available == true) {
                try {
                    condVar.await();
                } catch (InterruptedException e) { }
            }
            contents = value;
            available = true;
            System.out.format("Producer %d put: %d%n", who, contents);
            condVar.signalAll();
        } finally {
            aLock.unlock();
        }
    }
}
