import java.util.concurrent.*;
public class Producer3 extends Thread {
    private BlockingQueue<Integer> cubbyhole;
    private int number;
                
    public Producer3(BlockingQueue<Integer> c, int num) {
        cubbyhole = c;
        number = num;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                cubbyhole.put(i);
                System.out.format("Producer #%d put: %d%n", number, i);
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
    }
}
