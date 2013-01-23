
public class RunnableThreadTest1 {
    
    public static void main(String args[]) {
        
        PrintNameRunnable pnt1 = new PrintNameRunnable("A");
        Thread t1 = new Thread(pnt1);
        t1.start();
        
        PrintNameRunnable pnt2 = new PrintNameRunnable("B");
        Thread t2 = new Thread(pnt2);
        t2.start();
        
        PrintNameRunnable pnt3 = new PrintNameRunnable("C");
        Thread t3 = new Thread(pnt3);
        t3.start();
        
    }
}


