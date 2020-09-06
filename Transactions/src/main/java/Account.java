import lombok.*;

@Data
public class Account
{
    private long money;
    private String accNumber;
    private boolean accountBlocked;

    public Account(String accNumber, long money){
        this.accNumber = accNumber;
        this.money = money;
    }

    public void withdraw(long amount){
        checkAmountNoNegative(amount);
            if (money < amount) {
                throw new IllegalArgumentException("not enough money");
            } else this.money -= amount;

    }

    public void addMoney(long amount){
        checkAmountNoNegative(amount);
        this.money += amount;

    }

    private static void checkAmountNoNegative(long amount){
        if(amount < 0){
            throw new IllegalArgumentException("negative amount");
        }
    }
}
