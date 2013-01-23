/*
 * JoinTest.java
 *
 * Created on February 14, 2007, 8:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author sang
 */
public class JoinTest {
    
    /** Creates a new instance of JoinTest */
    public JoinTest() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        PrintNameThread pnt1 = new PrintNameThread("A");
        PrintNameThread pnt2 = new PrintNameThread("B");
        PrintNameThread pnt3 = new PrintNameThread("C");
        
        System.out.println("Running threads...");
        try {
            pnt1.thread.join();
            pnt2.thread.join();
            pnt3.thread.join();
        } catch (InterruptedException ie) {
        }
        System.out.println("Threads killed.");
        
    }
    
}
