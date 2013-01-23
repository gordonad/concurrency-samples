package concurrency.ch08_applying_thread_pools.solution_8_1;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * We used a synchronized weak hash map to hold keys of ThreadPoolStats objects
 * and as values the ObjectName.
 */
public class ThreadPoolStatsManager {
    private static final MBeanServer BEAN_SERVER = ManagementFactory.getPlatformMBeanServer();
    private static final Map<ThreadPoolStats, ObjectName> registeredPools =
            Collections.synchronizedMap(new WeakHashMap<ThreadPoolStats, ObjectName>());

    public static void register(ThreadPoolExecutor pool, String name) {
        try {
            ObjectName objectName = new ObjectName(
                    "java.util.concurrent.ThreadPoolExecutor:type=ThreadPoolStats-" + name);
            ThreadPoolStats stats = new ThreadPoolStats(pool);
            registeredPools.put(stats, objectName);
            BEAN_SERVER.registerMBean(stats, objectName);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void unregister(ThreadPoolStats pool) {
        try {
            ObjectName objectName = registeredPools.remove(pool);
            if (objectName != null) {
                BEAN_SERVER.unregisterMBean(objectName);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}