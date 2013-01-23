package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Our new solution is not just simpler, it also eliminates a deadlock that
 * we had in the transferTo() method.  You did see that, no?
 */
public class BankAccount {
    private final AtomicInteger balance = new AtomicInteger(0);

    public BankAccount(int balance) {
        this.balance.set(balance);
    }

    public void deposit(int amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public boolean transferTo(BankAccount other, int amount) {
        while (true) {
            int currentBalance = balance.get();
            if (currentBalance < amount) return false;
            int newBalance = currentBalance - amount;
            if (balance.compareAndSet(currentBalance, newBalance)) {
                other.deposit(amount);
                return true;
            }
        }
    }

    public int getBalance() {
        return balance.intValue();
    }
}