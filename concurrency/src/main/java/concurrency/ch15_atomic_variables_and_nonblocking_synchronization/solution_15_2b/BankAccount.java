package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.solution_15_2b;

import concurrency.util.UnsafeProvider;

/**
 * This approach has become more common in the Java 7 classes.  The
 * AtomicFieldUpdaters do a fair amount of checking to make sure that we don't
 * change the wrong field.  Unsafe ignores all that, assuming that if it works
 * once, it will always work.  We only do this for classes that absolutely need
 * to have that added bit of performance.
 */
public class BankAccount {
    private static final sun.misc.Unsafe UNSAFE;
    private static final long balanceOffset;

    static {
        try {
            UNSAFE = UnsafeProvider.getUnsafe();
            balanceOffset = UNSAFE.objectFieldOffset(BankAccount.class.getDeclaredField("balance"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private volatile int balance;

    public BankAccount(int balance) {
        this.balance = balance;
    }

    public void deposit(int amount) {
        while (true) {
            int current = UNSAFE.getInt(this, balanceOffset);
            int next = current + amount;
            if (UNSAFE.compareAndSwapInt(this, balanceOffset, current, next))
                return;
        }
    }

    public void withdraw(int amount) {
        deposit(-amount);
    }

    public boolean transferTo(BankAccount other, int amount) {
        while (true) {
            int currentBalance = UNSAFE.getInt(this, balanceOffset);
            if (currentBalance < amount) return false;
            int newBalance = currentBalance - amount;
            if (UNSAFE.compareAndSwapInt(this, balanceOffset, currentBalance, newBalance)) {
                other.deposit(amount);
                return true;
            }
        }
    }

    public int getBalance() {
        return balance;
    }
}
