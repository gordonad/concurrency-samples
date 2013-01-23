
public class SimpleThread extends Thread {
    
    public SimpleThread(String str) {
        super(str);
    }
    
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.format("%d %s%n", i, getName());
        }
        System.out.format("DONE! %s%n", getName());
    }
}
