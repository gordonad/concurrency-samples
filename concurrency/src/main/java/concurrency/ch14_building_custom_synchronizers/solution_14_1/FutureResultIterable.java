/*
 * This class forms part of the Java Concurrency Course by
 * Cretesoft Limited and may not be distributed without 
 * written consent.
 *
 * Copyright 2011, Heinz Kabutz, All rights reserved.
 */
package concurrency.ch14_building_custom_synchronizers.solution_14_1;

import net.jcip.annotations.GuardedBy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureResultIterable<V> implements Iterable<Future<V>> {
    private final ExecutorService delegate;
    private final CompletionService<V> service;
    private final ExecutorService resultFetcher = Executors.newSingleThreadExecutor();

    private final AtomicInteger numberOfSubmittedTasks = new AtomicInteger(0);
    @GuardedBy("completedJobs")
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
            private boolean wasInterrupted = false;

            @Override
            public void run() {
                while (!wasInterrupted || hasMoreResults()) {
                    fetchNextResult();
                }
            }

            private boolean hasMoreResults() {
                synchronized (completedJobs) {
                    return completedJobs.size() < numberOfSubmittedTasks.get();
                }
            }

            private void fetchNextResult() {
                try {
                    Future<V> future = service.take();
                    synchronized (completedJobs) {
                        completedJobs.add(future);
                        completedJobs.notifyAll(); // safer to use than notify()
                    }
                } catch (InterruptedException e) {
                    wasInterrupted = true;
                }
            }
        });
    }

    // the iterator returned from this method is *not* thread-safe
    @Override
    public Iterator<Future<V>> iterator() {
        return new Iterator<Future<V>>() {
            private int nextJob = 0;

            @Override
            public boolean hasNext() {
                return nextJob < numberOfSubmittedTasks.get();
            }

            @Override
            public Future<V> next() {
                if (!hasNext()) throw new NoSuchElementException();
                boolean wasInterrupted = false;
                synchronized (completedJobs) {
                    while (hasNext() && nextJob == completedJobs.size()) {
                        try {
                            completedJobs.wait();
                        } catch (InterruptedException e) {
                            wasInterrupted = true;
                        }
                    }
                    if (wasInterrupted) {
                        Thread.currentThread().interrupt();
                    }
                    return completedJobs.get(nextJob++);
                }
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
