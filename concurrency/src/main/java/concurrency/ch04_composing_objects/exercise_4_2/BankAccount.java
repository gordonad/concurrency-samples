package concurrency.ch04_composing_objects.exercise_4_2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO: Document pre-/post-conditions, invariants and synchronization policy.
 */
public class BankAccount {
    private final AtomicInteger balance = new AtomicInteger();

    public BankAccount(int balance) {
        this.balance.set(balance);
    }

    public void deposit(int amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(int amount) {
        int current, newBalance;
        do {
            current = balance.get();
            if (current < amount)
                throw new InsufficientFundsException();
            newBalance = current - amount;
        } while (!balance.compareAndSet(current, newBalance));
    }

    public void transferTo(BankAccount other, int amount) {
        withdraw(amount);
        other.deposit(amount);
    }
}