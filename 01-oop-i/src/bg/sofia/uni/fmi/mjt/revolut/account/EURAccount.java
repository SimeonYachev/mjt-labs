package bg.sofia.uni.fmi.mjt.revolut.account;

public class EURAccount extends Account {

    public EURAccount(String IBAN) {
        super(IBAN,0);
    }

    public EURAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    public boolean equals(EURAccount other){
        return super.equals(other);
    }

    public String getCurrency() {
        return "EUR";
    }

    public double getAmount() {
        return super.getAmount();
    }

    public void addMoney(double amount) {
        super.addMoney(amount);
    }

    public void subtractMoney(double amount){
        super.subtractMoney(amount);
    }
}
