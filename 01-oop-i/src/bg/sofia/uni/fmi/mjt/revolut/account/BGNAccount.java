package bg.sofia.uni.fmi.mjt.revolut.account;

public class BGNAccount extends Account {

    public BGNAccount(String IBAN) {
        super(IBAN,0);
    }

    public BGNAccount(String IBAN, double amount) {
        super(IBAN, amount);
    }

    public boolean equals(BGNAccount other){
        return super.equals(other);
    }

    public String getCurrency() {
        return "BGN";
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
