package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.exercise_15_3;


/**
 * TODO: Use an AtomicReferenceArray to make sure that changes to the currencies
 * TODO: array are visible to other threads.
 */
public class Currencies {
    private final String[] currencies =
            {"USD", "EUR", "GBP", "CHF"};

    public String[] getCurrencies() {
        return currencies.clone();
    }

    public String getWorldCurrency() {
        return currencies[0];
    }

    public void setWorldCurrency(String worldCurrency) {
        currencies[0] = worldCurrency;
    }
}