import java.util.Date;
import java.util.Random;
/*
 * Producer.java
 *
 * Created on April 17, 2007, 4:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author marius
 */
public class Producer implements Runnable {
    private Monitor monitor;
    
    /** Creates a new instance of Producer */
    public Producer(Monitor m) {
        monitor=m;
        
    }
    
    public void run() {
        Random rand = new Random();
        for(int i=0;i<3;i++) {
            //use the synchronized method put () to
            //put the actual date in a Date type reference variable
            monitor.put(new Date());
            //print the action
            System.out.format("Producer %d put the time %s%n", i, Monitor.datum);
            //allow the Consumer to take a random break
            try {
                Thread.sleep(rand.nextInt(5000));
            } catch (InterruptedException e) {
            }
        }
    }
}
