import java.util.concurrent.*;
public class Consumer3 extends Thread {
    private BlockingQueue<Integer> cubbyhole;
    private int number;
		
    public Consumer3(BlockingQueue<Integer> c, int num) {
        cubbyhole = c;
        number = num;
    }

    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            try {
                value = cubbyhole.take();
                System.out.format("Consumer #%d got: %d%n", number, value);  
            } catch (InterruptedException e) { }        
        }
    }
}
