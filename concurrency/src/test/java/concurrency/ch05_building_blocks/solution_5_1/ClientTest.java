package concurrency.ch05_building_blocks.solution_5_1;

import concurrency.ch05_building_blocks.exercise_5_1.Alert;
import concurrency.ch05_building_blocks.exercise_5_1.AlertLevel;
import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.fail;

/**
 * DO NOT CHANGE.
 */
public class ClientTest {
    @Test
    public void checkForConcurrentModificationException() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<Void> future = pool.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Client client = new Client();
                for (int i = 0; i < 100_000_000; i++) {
                    client.checkAlerts();
                }
                return null;
            }
        });
        pool.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AlertProvider prov = AlertProvider.getInstance();
                Alert alert = new Alert("fly loose in the server room", AlertLevel.GREEN);
                for (int i = 0; i < 100_000_000; i++) {
                    prov.addAlert(alert);
                    prov.removeAlert(alert);
                }
                return null;
            }
        });
        pool.shutdown();
        try {
            future.get();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            fail("Exception occured: " + e.getCause());
        }
    }
}
