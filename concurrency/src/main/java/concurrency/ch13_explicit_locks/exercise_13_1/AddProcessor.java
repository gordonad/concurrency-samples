package concurrency.ch13_explicit_locks.exercise_13_1;

/**
 * DO NOT CHANGE.
 */
public class AddProcessor<N extends Number>
        implements Processor<N> {
    private double total = 0;

    @Override
    public boolean process(N n) {
        total += n.doubleValue();
        return true;
    }

    public double getTotal() {
        return total;
    }

    public void reset() {
        total = 0;
    }
}

