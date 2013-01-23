package concurrency.ch05_building_blocks.exercise_5_2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * This task should be comparable according to priority and insertion order.
 * Try and understand what
 */
class PrioritizedFutureTask<T> extends FutureTask<T> {
    public PrioritizedFutureTask(Runnable runnable, T result, Priority priority) {
        super(runnable, result);
    }

    public PrioritizedFutureTask(Callable<T> callable, Priority priority) {
        super(callable);
    }
}