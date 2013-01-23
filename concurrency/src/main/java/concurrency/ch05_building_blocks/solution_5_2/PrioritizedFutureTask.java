package concurrency.ch05_building_blocks.solution_5_2;

import concurrency.ch05_building_blocks.exercise_5_2.Priority;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * We need to sort the tasks according to the priority AND the insertion order.
 */
class PrioritizedFutureTask<T> extends FutureTask<T>
        implements Comparable<PrioritizedFutureTask<T>> {
    private final Priority priority;
    private final static AtomicInteger nextSequence = new AtomicInteger(0);
    private final long sequence = nextSequence.incrementAndGet();

    public PrioritizedFutureTask(Runnable runnable, T result, Priority priority) {
        super(runnable, result);
        this.priority = priority;
    }

    public PrioritizedFutureTask(Callable<T> callable, Priority priority) {
        super(callable);
        this.priority = priority;
    }

    @Override
    public int compareTo(PrioritizedFutureTask o) {
        int result = priority.compareTo(o.priority);
        if (result != 0) return result;
        if (sequence < o.sequence) return -1;
        if (sequence > o.sequence) return 1;
        // it should not be possible to have two tasks with the same priority and
        // insertion order
        throw new IllegalStateException();
    }
}
