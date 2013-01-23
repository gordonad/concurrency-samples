package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_2;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * This avoids using an AtomicInteger, instead it uses the balanceUpdater to
 * do an addAndGet() and a compareAndSet() on the volatile field balance.
 */
public class BankAccount {
    private static final AtomicIntegerFieldUpdater<BankAccount> balanceUpdater =
            AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "balance");

    private volatile int balance = 0;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        balanceUpdater.addAndGet(this, amount);
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public boolean transferTo(BankAccount other, int amount) {
        while (true) {
            int currentBalance = balanceUpdater.get(this);
            if (currentBalance < amount) return false;
            int newBalance = currentBalance - amount;
            if (balanceUpdater.compareAndSet(this, currentBalance, newBalance)) {
                other.deposit(amount);
                return true;
            }
        }
    }

    public int getBalance() {
        return balance;
    }
}
