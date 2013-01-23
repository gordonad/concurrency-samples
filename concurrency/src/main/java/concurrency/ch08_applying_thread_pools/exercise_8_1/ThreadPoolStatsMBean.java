package concurrency.ch08_applying_thread_pools.exercise_8_1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * DO NOT CHANGE.
 */
public interface ThreadPoolStatsMBean {
    public void shutdown();

    public int prestartAllCoreThreads();

    public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerType();

    public Class<? extends BlockingQueue> getQueueType();

    public void setCorePoolSize(int corePoolSize);

    public int getCorePoolSize();

    public long getKeepAliveTimeInMilliseconds();

    public int getPoolSize();

    public int getActiveCount();

    public int getLargestPoolSize();

    public long getTaskCount();

    public long getCompletedTaskCount();
}