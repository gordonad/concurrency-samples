public class Consumer2 extends Thread {

    private CubbyHole2 cubbyhole;
    private int number;

    public Consumer2(CubbyHole2 c, int number) {
        cubbyhole = c;
        this.number = number;
    }

    public void run() {
        int value = 0;
        for (int i = 0; i < 10; i++) {
            value = cubbyhole.get(number);
        }
    }
}
