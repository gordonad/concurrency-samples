package concurrency.ch13_explicit_locks.exercise_13_1;

/**
 * DO NOT CHANGE.
 */
public class PrintProcessor implements Processor<Object> {
    @Override
    public boolean process(Object o) {
        System.out.println(">>> " + o);
        return true;
    }
}
