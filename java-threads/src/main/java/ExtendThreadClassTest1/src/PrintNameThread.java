
public class PrintNameThread extends Thread {
    
    PrintNameThread(String name) {
        super(name);
    }
    
    // Override the run() methof the Thread class
    public void run() {
        String name = getName();
        for (int i = 0; i < 10; i++) {
            System.out.print(name);
        }
    }
}
