package concurrency.ch08_applying_thread_pools.solution_8_1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * In the solution, I included additional properties, such as
 * allowsCoreThreadTimeOut().  I left this out in the exercise, because most of
 * the code is repetitive.
 */
public interface ThreadPoolStatsMBean {
    public void shutdown();

    public List<Runnable> shutdownNow();

    public boolean isShutdown();

    public boolean isTerminating();

    public boolean isTerminated();

    public Class<? extends RejectedExecutionHandler> getRejectedExecutionHandlerType();

    public void setCorePoolSize(int corePoolSize);

    public int getCorePoolSize();

    public boolean prestartCoreThread();

    public int prestartAllCoreThreads();

    public boolean allowsCoreThreadTimeOut();

    public void allowCoreThreadTimeOut(boolean value);

    public void setMaximumPoolSize(int maximumPoolSize);

    public int getMaximumPoolSize();

    public long getKeepAliveTimeInMilliseconds();

    public Class<? extends BlockingQueue> getQueueType();

    public int getPoolSize();

    public int getActiveCount();

    public int getLargestPoolSize();

    public long getTaskCount();

    public long getCompletedTaskCount();
}