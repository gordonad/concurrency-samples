package stm;


import clojure.lang.Ref;

import java.util.concurrent.Callable;

import static clojure.lang.LockingTransaction.runInTransaction;

public class Account {
    private Ref balance;

    public Account(int initialBalance) throws Exception {
        balance = new Ref(initialBalance);
    }

    public void deposit(final int amount) throws Exception {
        runInTransaction(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                int currentBalance = (Integer) balance.deref();
                if (amount > 0) balance.set(currentBalance + amount);
                System.out.println("Look how much money I have!!!" + balance.deref());
                return true;
            }
        });
    }

    public void withdraw(final int amount) throws Exception {
        runInTransaction(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                int currentBalance = (Integer) balance.deref();
                if (amount > 0 && amount <= currentBalance)
                    balance.set(currentBalance - amount);
                else
                    throw new RuntimeException("Good try, you need money!");

                return true;
            }
        });
    }

    public int getBalance() {
        return (Integer) (balance.deref());
    }
}
