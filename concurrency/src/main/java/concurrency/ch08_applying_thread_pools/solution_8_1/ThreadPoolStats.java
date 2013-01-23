package concurrency.ch08_applying_thread_pools.solution_8_1;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A complete mbean that allows us to manage a thread pool using JConsole or
 * any MBean viewer.  It includes the additional properties that were left out
 * for the exercise.
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
    public List<Runnable> shutdownNow() {
        return getPool().shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return getPool().isShutdown();
    }

    @Override
    public boolean isTerminating() {
        return getPool().isTerminating();
    }

    @Override
    public boolean isTerminated() {
        return getPool().isTerminated();
    }

    @Override
    public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerType() {
        return getPool().getRejectedExecutionHandler().getClass();
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        getPool().setCorePoolSize(corePoolSize);
    }

    @Override
    public int getCorePoolSize() {
        return getPool().getCorePoolSize();
    }

    @Override
    public boolean prestartCoreThread() {
        return getPool().prestartCoreThread();
    }

    @Override
    public int prestartAllCoreThreads() {
        return getPool().prestartAllCoreThreads();
    }

    @Override
    public boolean allowsCoreThreadTimeOut() {
        return getPool().allowsCoreThreadTimeOut();
    }

    @Override
    public void allowCoreThreadTimeOut(boolean value) {
        getPool().allowCoreThreadTimeOut(value);
    }

    @Override
    public void setMaximumPoolSize(int maximumPoolSize) {
        getPool().setMaximumPoolSize(maximumPoolSize);
    }

    @Override
    public int getMaximumPoolSize() {
        return getPool().getMaximumPoolSize();
    }

    @Override
    public long getKeepAliveTimeInMilliseconds() {
        return getPool().getKeepAliveTime(TimeUnit.MILLISECONDS);
    }

    @Override
    public Class<? extends BlockingQueue> getQueueType() {
        return getPool().getQueue().getClass();
    }

    @Override
    public int getPoolSize() {
        return getPool().getPoolSize();
    }

    @Override
    public int getActiveCount() {
        return getPool().getActiveCount();
    }

    @Override
    public int getLargestPoolSize() {
        return getPool().getLargestPoolSize();
    }

    @Override
    public long getTaskCount() {
        return getPool().getTaskCount();
    }

    @Override
    public long getCompletedTaskCount() {
        return getPool().getCompletedTaskCount();
    }
}