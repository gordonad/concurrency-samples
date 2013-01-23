package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.exercise_15_1;

/**
 * TODO: Change BankAccount to use atomics, rather than explicit locking.
 */
public class BankAccount {
    private int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public synchronized int getBalance() {
        return balance;
    }

    public synchronized boolean transferTo(BankAccount other, int amount) {
        if (balance < amount) {
            return false;
        }
        withdraw(amount);
        other.deposit(amount);
        return true;
    }
}