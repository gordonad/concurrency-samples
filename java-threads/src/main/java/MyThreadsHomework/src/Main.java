/*
 * Main.java
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
public class Main 
{
    
    /** Creates a new instance of Main */
    public Main() {
    }
    public static void main (String []a)
    {
    Monitor moni = new Monitor();
    ThreadGroup group = new ThreadGroup("timeGroup");
   Thread p1=new Thread(group, new Producer (moni), "p1");
   Thread c1=new Thread(group, new Consumer(moni), "c1");
    p1.start();
    c1.start();
    ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
    ThreadGroup []t={group, currentGroup};
    for(int i=0;i<t.length;i++)
    {
    Thread [] tarray = new Thread[10];
   int actualSize=t[i].enumerate(tarray);
   System.out.println("In thread group "+t[i].getName()+" we have the following threads: ");
   for (int j=0;j<actualSize;j++)
   {
     System.out.print(tarray[j].getName()+" ");  
   }
   System.out.println("\n");
    }
 
  }
    }
    

