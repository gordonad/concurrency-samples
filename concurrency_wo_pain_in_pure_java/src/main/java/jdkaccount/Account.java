package jdkaccount;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account implements Comparable<Account> {
    private int balance;
    final public Lock monitor = new ReentrantLock();

    public Account(final int initialBalance) {
        balance = initialBalance;
    }

    @Override
    public int compareTo(final Account other) {
        return new Integer(hashCode()).compareTo(other.hashCode());
    }

    public void deposit(final int amount) {
        monitor.lock();
        try {
            if (amount > 0) balance += amount;
        } finally { //In case there was an InterruptedException you're covered
            monitor.unlock();
        }
    }

    public boolean withdraw(final int amount) {
        try {
            monitor.lock();
            if (amount > 0 && balance >= amount) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            monitor.unlock();
        }
    }

    public int getBalance() {
        return balance;
    }
}
