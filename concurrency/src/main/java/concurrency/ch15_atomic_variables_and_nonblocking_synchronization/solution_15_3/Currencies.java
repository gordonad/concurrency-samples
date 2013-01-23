package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_3;

import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * In this solution, we store the String[] elements inside an
 * AtomicReferenceArray.  This allows us to have individual elements of the
 * array as volatile.
 */
public class Currencies {
    private final AtomicReferenceArray<String> elements =
            new AtomicReferenceArray<>(
                    new String[]{"USD", "EUR", "GBP", "CHF"});

    public String[] getCurrencies() {
        String[] result = new String[elements.length()];
        for (int i = 0; i < result.length; i++) {
            result[i] = elements.get(i);
        }
        return result;
    }

    public void setWorldCurrency(String worldCurrency) {
        elements.set(0, worldCurrency);
    }

    public String getWorldCurrency() {
        return elements.get(0);
    }
}