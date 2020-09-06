import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static final int MAX_VALUE_RANDOM = 80000;
    private static int threadCount = 10;
    private static int transferPerThread = 1000;

    private static long allMoney = 0;


    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank();
        Random random = new Random();
        ArrayList<Account> accounts = new ArrayList();

        accounts.addAll(bank.createAccounts().values());

        ArrayList<Thread> threads = new ArrayList<>();

        for(int indexThread = 0; indexThread < threadCount; indexThread++){
            threads.add(new Thread(() -> {
                for(int indexTransfer = 0; indexTransfer < transferPerThread; indexTransfer++) {
                    Account from = accounts.get(random.nextInt(accounts.size()));
                    Account to = accounts.get(random.nextInt(accounts.size()));
                    int amount = random.nextInt(MAX_VALUE_RANDOM);

                    try {
                        bank.transfer(from.getAccNumber(), to.getAccNumber(), amount);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        threads.forEach(Thread::start);
        for(Thread thread : threads){
            thread.join();
        }

        }

}




