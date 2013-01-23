package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_4;

/**
 * Another solution is to have a volatile boolean guard.  We write to it after
 * we have written to the array element and then we read from it before reading
 * the array element.  This ensures that we see the most up-to-date values of
 * the array.
 */
public class Currencies {
    private final String[] currencies =
            {"USD", "EUR", "GBP", "CHF"};
    private volatile boolean currencyGuard;

    public String[] getCurrencies() {
        boolean read = currencyGuard;
        return currencies.clone();
    }

    public void setWorldCurrency(String worldCurrency) {
        currencies[0] = worldCurrency;
        currencyGuard = true;
    }

    public String getWorldCurrency() {
        boolean read = currencyGuard;
        return currencies[0];
    }
}

