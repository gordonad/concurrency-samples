public class ProducerConsumerSynchronized2 {

    public static void main(String[] args) {
        
        CubbyHole2 c = new CubbyHole2();
        
        Producer2 p1 = new Producer2(c, 1);
        Consumer2 c1 = new Consumer2(c, 1);

        p1.start();
        c1.start();
    }
}
