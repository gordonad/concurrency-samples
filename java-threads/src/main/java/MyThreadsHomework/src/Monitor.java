import java.util.Date;
/*
 * Monitor.java
 *
 * Created on April 17, 2007, 4:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author marius
 */
public class Monitor {
    
    static Date datum=null;
    boolean lock=false;
    /** Creates a new instance of Monitor */
    
    public synchronized Date get(){
        while(lock==false){
            try{
                wait();
            } catch (InterruptedException e){
            }
        }
        lock=false;
        notifyAll();
        return datum;
    }
    
    public synchronized void put(Date n){
        datum=n;
        while(lock==true){
            //release the lock so the Consumer
            //can use the new Date
            try{
                wait();
            } catch (InterruptedException e){
            }
        }
        
        lock=true;
        //inform all the threads that the lock is available
        notifyAll();
    }
}
