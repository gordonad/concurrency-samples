public class ThreeThreadsTest {
    
    public static void main (String[] args) {
        
        // Start three threads
        new SimpleThread("Boston").start();
        new SimpleThread("New York").start();
        new SimpleThread("Seoul").start();
        
    }
}
