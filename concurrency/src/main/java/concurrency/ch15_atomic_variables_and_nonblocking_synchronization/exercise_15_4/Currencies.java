package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.exercise_15_4;

/**
 * TODO: Instead of the AtomicReferenceArray, use an atomic guard field to
 * TODO: ensure that changes to the array are flushed
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