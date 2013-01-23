package jdkaccount;

public class Sample {
    public static void main(String[] args) throws Exception {
        Account account1 = new Account(1000);
        Account account2 = new Account(1000);

        AccountService.transfer(account1, account2, 500);

        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());

        AccountService.transfer(account1, account2, 2000);
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
    }
}
