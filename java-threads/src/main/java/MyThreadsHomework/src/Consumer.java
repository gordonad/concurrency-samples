import java.util.Date;
import java.util.Random;
/*
 * Consumer.java
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
public class Consumer implements Runnable {
    
    private Monitor monitor;
    // private Date date;
    
    /** Creates a new instance of Consumer */
    public Consumer(Monitor m) {
        monitor=m;
    }
    
    public void run() {
        Random r= new Random();
        
        for(int i=0;i<3;i++) {
            
            System.out.format( "Consumer %d got the time :  %s%n", i, monitor.get());
            
            //alow the Producer to take a random break
            //so the time will be different each time
            try {
                Thread.sleep(r.nextInt(5000));
            } catch (InterruptedException e) {
                
            }
            
        }
        
    }
    
}
