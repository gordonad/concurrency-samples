package stm;

import java.util.concurrent.Callable;

import static clojure.lang.LockingTransaction.runInTransaction;

public class AccountService {
    public static void transfer(final Account from, final Account to, final int amount) throws Exception {
        try {
            runInTransaction(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    to.deposit(amount);
                    from.withdraw(amount);
                    return true;
                }
            });
        } catch (Exception ex) {
            System.out.println("Oops, transaction failed...");
        }
    }
}
