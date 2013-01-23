package SemaphoreExample;

import java.util.concurrent.Semaphore;

/**
 * Use a Semaphore to control the usage of a pool of resources.  If a
 * consumer requests a resource when all are being used the consumer
 * will block until a resource is returned and made available.
 */
public class ResourcePool {
    private int poolSize;
    private Semaphore available;
    private Integer[] resources;
    private boolean[] used;

    /**
     * Constructor
     *
     * @param poolSize Size of fixed pool of resources
     */
    public ResourcePool(int poolSize) {
        this.poolSize = poolSize;

    /*  Create a pool of resources (for this example just a set of Integer
     *  objects.  Create a new semaphore to control access to the resources
     */
        available = new Semaphore(poolSize);

        used = new boolean[poolSize];
        resources = new Integer[poolSize];

        for (int i = 0; i < poolSize; i++)
            resources[i] = i;
    }

    /**
     * Get a resource.  If all are currently being used this will block
     * until one is returned to the pool.  This is a synchronised method
     * to make the code fully thread safe.
     *
     * @return The resource to use
     */
    public Integer getResource() {
        try {
            available.acquire();
        } catch (InterruptedException ie) {
            // Ignore
        }

        for (int i = 0; i < poolSize; i++) {
            if (!used[i]) {
                used[i] = true;
                return resources[i];
            }
        }

        return null;
    }

    /**
     * Return a resource to the pool
     *
     * @param resource The resource being returned to the pool
     */
    public void putResource(Integer resource) {
    /*  Note use of auto-unboxing  */
        used[resource] = false;
        available.release();
    }
}