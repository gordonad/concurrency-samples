package concurrency.ch15_atomic_variables_and_nonblocking_synchronization.exercise_15_2;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * DO NOT CHANGE.
 */
public class BankAccountTest {
    private static final long[] EMPTY_LONGS = new long[0];
    private static final int NUMBER_OF_DEPOSIT_WITHDRAWS = 10_000_000;

    @Test
    public void testForDeadlock() throws InterruptedException {
        final BankAccount switzerland = new BankAccount(1_000_000);
        final BankAccount greece = new BankAccount(1000);

        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    switzerland.transferTo(greece, 100);
                }
            }
        };
        Thread t2 = new Thread("t2") {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    greece.transferTo(switzerland, 100);
                }
            }
        };

        t1.start();
        t2.start();

        t1.join(1000);

        for (long thread_id : findDeadlockedThreads()) {
            if (thread_id == t1.getId()) {
                fail("Thread t1 deadlocked");
            }
            if (thread_id == t2.getId()) {
                fail("Thread t2 deadlocked");
            }
        }
    }

    private static long[] findDeadlockedThreads() {
        ThreadMXBean tbean = ManagementFactory.getThreadMXBean();
        long[] infos = tbean.findMonitorDeadlockedThreads();
        return infos == null ? EMPTY_LONGS : infos;
    }

    @Test
    public void testForCorrectness() throws InterruptedException {
        final BankAccount account = new BankAccount(1000);
        Runnable depositWithdraw = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < NUMBER_OF_DEPOSIT_WITHDRAWS; i++) {
                    account.deposit(100);
                    account.withdraw(100);
                }
            }
        };

        Thread t1 = new Thread(depositWithdraw, "t1");
        Thread t2 = new Thread(depositWithdraw, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testStructure() {
        boolean foundVolatileInt = false;
        boolean foundFieldUpdater = false;

        for (Field field : BankAccount.class.getDeclaredFields()) {
            if (field.getType() == AtomicInteger.class) {
                fail("Need to replace AtomicInteger with plain volatile int");
            }
            if (field.getType() == int.class && Modifier.isVolatile(field.getModifiers())) {
                foundVolatileInt = true;
            }
        }
        assertTrue("Need to declare a volatile int", foundVolatileInt);
    }

    @Test
    public void testForPerformance() throws InterruptedException {
        long originalTime = measureOriginalBankAccount();

        final BankAccount[] accounts = new BankAccount[10];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new BankAccount(1000);
        }
        Runnable depositWithdraw = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < NUMBER_OF_DEPOSIT_WITHDRAWS; i++) {
                    for (BankAccount account : accounts) {
                        account.deposit(100);
                        account.withdraw(100);
                    }
                }
            }
        };

        Thread t1 = new Thread(depositWithdraw, "t1");
        Thread t2 = new Thread(depositWithdraw, "t2");

        t1.start();
        t2.start();

        long time = System.currentTimeMillis();
        t1.join();
        t2.join();
        time = System.currentTimeMillis() - time;
        System.out.println("Original time: " + originalTime);
        System.out.println("New time = " + time);

        assertTrue(((double) time) * 1.1 < originalTime);
    }

    private long measureOriginalBankAccount() throws InterruptedException {
        final OriginalBankAccount[] accounts = new OriginalBankAccount[10];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new OriginalBankAccount(1000);
        }
        Runnable depositWithdraw = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < NUMBER_OF_DEPOSIT_WITHDRAWS; i++) {
                    for (OriginalBankAccount account : accounts) {
                        account.deposit(100);
                        account.withdraw(100);
                    }
                }
            }
        };

        Thread t1 = new Thread(depositWithdraw, "t1");
        Thread t2 = new Thread(depositWithdraw, "t2");

        t1.start();
        t2.start();

        long time = System.currentTimeMillis();
        t1.join();
        t2.join();
        time = System.currentTimeMillis() - time;
        return time;
    }

    private static class OriginalBankAccount {
        private int balance;

        public OriginalBankAccount(int balance) {
            this.balance = balance;
        }

        public synchronized void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            deposit(-amount);
        }

        public synchronized boolean transferTo(OriginalBankAccount other, int amount) {
            if (balance < amount) {
                return false;
            }
            withdraw(amount);
            other.deposit(amount);
            return true;
        }
    }
}
