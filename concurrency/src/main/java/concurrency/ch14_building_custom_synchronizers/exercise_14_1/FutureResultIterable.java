package concurrency.ch14_building_custom_synchronizers.exercise_14_1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureResultIterable<V> implements Iterable<Future<V>> {
    private final ExecutorService delegate;
    private final CompletionService<V> service;
    private final ExecutorService resultFetcher = Executors.newSingleThreadExecutor();

    private final AtomicInteger numberOfSubmittedTasks = new AtomicInteger(0);
    private final List<Future<V>> completedJobs = new ArrayList<>();

    /**
     * DO NOT CHANGE.
     */
    public FutureResultIterable() {
        delegate = Executors.newCachedThreadPool();
        service = new ExecutorCompletionService<V>(delegate);
    }

    /**
     * DO NOT CHANGE.
     */
    public void submit(Callable<V> task) {
        service.submit(task);
        if (numberOfSubmittedTasks.getAndIncrement() == 0) {
            startResultFetcherThread(); // to avoid starting it in the constructor
        }
    }

    private void startResultFetcherThread() {
        resultFetcher.submit(new Runnable() {
            @Override
            public void run() {
                // TODO: This should fetch completed jobs from the CompletionService
                // TODO: and add them to the completedJobs collection, notifying any
                // TODO: threads waiting for the results.  It should only terminate
                // TODO: once all the results have been published into completedJobs.
            }
        });
    }

    @Override
    public Iterator<Future<V>> iterator() {
        return new Iterator<Future<V>>() {
            @Override
            public boolean hasNext() {
                // TODO: compare our current position to number of submitted tasks
                throw new UnsupportedOperationException("todo");
            }

            @Override
            public Future<V> next() {
                // TODO: read all completed jobs immediately, then block until results
                // TODO: have been published
                throw new UnsupportedOperationException("todo");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("not supported");
            }
        };
    }

    /**
     * DO NOT CHANGE.
     */
    public void shutdown() {
        resultFetcher.shutdownNow();
        delegate.shutdown();
    }

    /**
     * DO NOT CHANGE.
     */
    public boolean isTerminated() {
        return resultFetcher.isTerminated() && delegate.isTerminated();
    }
}
