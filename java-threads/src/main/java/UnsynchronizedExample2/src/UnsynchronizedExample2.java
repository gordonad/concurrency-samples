
public class UnsynchronizedExample2 extends Thread {
    
    static int n = 1;
    
    public void run() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <1000000; j++){j = j++;}
            System.out.println(n);
            n++;
        }
    }
    
    public static void main(String args[]) {
        Thread thr1 = new UnsynchronizedExample2();
        Thread thr2 = new UnsynchronizedExample2();
        
        thr1.start();
        thr2.start();

    }
    
}
