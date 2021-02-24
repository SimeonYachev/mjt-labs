package bg.sofia.uni.fmi.mjt.revolut.account;

public abstract class Account {

    private double amount;
    private String IBAN;

    public Account(String IBAN) {
        this(IBAN, 0);
    }

    public Account(String IBAN, double amount) {
        this.IBAN = IBAN;
        this.amount = amount;
    }

    public boolean equals(Account other) {
        if(other == null) return false;
        return this.IBAN.equals(other.IBAN);
    }

    public abstract String getCurrency();

    public double getAmount() {
        return amount;
    }

    public void addMoney(double amount){
        this.amount += amount;
    }

    public void subtractMoney(double amount){
        this.amount -= amount;
    }

}