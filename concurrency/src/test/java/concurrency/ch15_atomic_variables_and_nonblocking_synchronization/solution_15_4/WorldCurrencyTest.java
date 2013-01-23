package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_4;

import org.junit.Test;

import java.util.concurrent.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * DO NOT CHANGE.
 */
public class WorldCurrencyTest {
    @Test
    public void testThatWorldCurrencyIsChanged() throws InterruptedException, ExecutionException, TimeoutException {
        Currencies currencies = new Currencies();
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<String> future = pool.submit(new WorldCurrencyWatch(currencies));
        Thread.sleep(1_000);
        currencies.setWorldCurrency("EUR");
        assertEquals("World currency did not change", future.get(1, TimeUnit.SECONDS), "EUR");
        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.SECONDS);
        assertTrue("Visibility problem - Watcher thread should have finished by now", pool.isTerminated());
    }

    @Test
    public void testThatArrayDoesNotEscape() throws InterruptedException {
        Currencies currencies = new Currencies();
        currencies.getCurrencies()[0] = "ZAR";
        assertEquals("You allowed the String[] to escape - that is not threadsafe", "USD", currencies.getWorldCurrency());
    }

    private static class WorldCurrencyWatch implements Callable<String> {
        private final Currencies currencies;

        public WorldCurrencyWatch(Currencies currencies) {
            this.currencies = currencies;
        }

        @Override
        public String call() throws Exception {
            while (currencies.getWorldCurrency() == "USD") ;
            System.out.println("World currency has changed to " +
                    currencies.getCurrencies()[0]);
            return currencies.getWorldCurrency();
        }
    }
}