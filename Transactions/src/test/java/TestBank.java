import org.junit.*;
import java.util.*;


public class TestBank {

        private static long allMoney = 0;
        Bank bank = new Bank();
        ArrayList<Account> accounts = new ArrayList();

        @Before
        public void setUp(){

            accounts.addAll(bank.createAccounts().values());

            allMoney = accounts.stream().mapToLong(nameAccount ->
                    bank.getBalance(nameAccount.getAccNumber())).sum();
        }


        @Test
        public void TestAllMoneyAfterTransaction() throws InterruptedException {
            final int MAX_VALUE_RANDOM = 80000;
            int threadCount = 10;
            int transferPerThread = 1000;

            Random random = new Random();
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



            long testAllMoney = accounts.stream().mapToLong(nameAccount ->
                    bank.getBalance(nameAccount.getAccNumber())).sum();
            Assert.assertEquals(allMoney, testAllMoney);

        }
    }

