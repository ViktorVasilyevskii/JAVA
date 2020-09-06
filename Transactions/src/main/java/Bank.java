import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    private final int COUNT_ACCOUNT = 10;
    private final int MIN_AMOUNT = 100000;
    private final int MAX_AMOUNT = 300000;
    private final int FRAUD_AMOUNT = 50000;


    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
        throws InterruptedException
    {
        Thread.sleep(100);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Account from = accounts.get(fromAccountNum);
        Account to = accounts.get(toAccountNum);


        if(from.isAccountBlocked() || to.isAccountBlocked()){
            System.out.printf("Операция заблокирована. Аккаунт %s \t %s заблокирован!\n", from.getAccNumber(), to.getAccNumber());
        }else if(amount > FRAUD_AMOUNT) {
            boolean fraud = isFraud(from.getAccNumber(), to.getAccNumber(), amount);
            from.setAccountBlocked(fraud);
            to.setAccountBlocked(fraud);

            accounts.put(fromAccountNum, from);
            accounts.put(toAccountNum, to);
        }else {
            synchronized (from){
                synchronized (to){
                    from.withdraw(amount);
                    to.addMoney(amount);
                }
            }
                    accounts.put(fromAccountNum, from);
                    accounts.put(toAccountNum, to);

        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        if(!accounts.containsKey(accountNum)) {
            throw new NoSuchElementException("No such ACCOUNT");
        }else {
            return accounts.get(accountNum).getMoney();
        }

    }

    public HashMap<String, Account> createAccounts() {
        for(int indexAccount = 0; indexAccount < COUNT_ACCOUNT; indexAccount++)
        {
            String accNumber = "account" + indexAccount;
            long money = (long)(MIN_AMOUNT + Math.random() * MAX_AMOUNT);
            Account account = new Account(accNumber, money);
            accounts.put(accNumber, account);
        }
        return accounts;
    }
}
