
// : c13:YieldingThread.java
// Suggesting when to switch threads with yield().
// From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
// www.BruceEckel.com. See copyright notice in CopyRight.txt.

public class YieldingThread extends Thread {
    
    private int countDown = 5;
    
    private static int threadCount = 0;
    
    public YieldingThread() {
        super("" + ++threadCount);
        start();
    }
    
    public String toString() {
        return "#" + getName() + ": " + countDown;
    }
    
    public void run() {
        while (true) {
            System.out.println(this);
            if (--countDown == 0)
                return;
            
            yield();
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++)
            new YieldingThread();
    }
} ///:~
