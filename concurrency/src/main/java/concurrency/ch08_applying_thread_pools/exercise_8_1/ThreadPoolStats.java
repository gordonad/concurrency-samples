package concurrency.ch08_applying_thread_pools.exercise_8_1;

import java.lang.ref.WeakReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TODO: Implement all the incomplete methods, delegating to the thread pool
 * TODO: returned by the getPool() method.
 */
public class ThreadPoolStats implements ThreadPoolStatsMBean {
    private final WeakReference<ThreadPoolExecutor> ref;

    /**
     * DO NOT CHANGE.
     */
    public ThreadPoolStats(ThreadPoolExecutor pool) {
        ref = new WeakReference<ThreadPoolExecutor>(pool);
    }

    /**
     * DO NOT CHANGE.
     */
    private ThreadPoolExecutor getPool() {
        ThreadPoolExecutor pool = ref.get();
        if (pool == null) {
            ThreadPoolStatsManager.unregister(this);
            throw new IllegalStateException("Pool has already been garbage collected");
        }
        return pool;
    }

    /**
     * DO NOT CHANGE.
     */
    @Override
    public void shutdown() {
        getPool().shutdown();
    }

    @Override
    public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerType() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getCorePoolSize() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int prestartAllCoreThreads() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public long getKeepAliveTimeInMilliseconds() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public Class<? extends BlockingQueue> getQueueType() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getPoolSize() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getActiveCount() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public int getLargestPoolSize() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public long getTaskCount() {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public long getCompletedTaskCount() {
        throw new UnsupportedOperationException("TODO");
    }
}