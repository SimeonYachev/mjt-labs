package bg.sofia.uni.fmi.mjt.revolut;

import java.time.LocalDate;
import java.util.Arrays;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.account.Account;


public class Revolut implements RevolutAPI {

    Account[] accounts;
    Card[] cards;

    public Revolut(Account[] accounts, Card[] cards){
        this.accounts = Arrays.copyOf(accounts, accounts.length);
        this.cards = Arrays.copyOf(cards, cards.length);
    }

    /** An Account exists when it's not null and appears in the array of accounts **/
    public boolean exists(Account account) {
        if(account == null) return false;
        for(Account a : this.accounts) {
            if(account.equals(a)){
                return true;
            }
        }
        return false;
    }

    /** A Card is valid when it's not null, exists in the array of cards and is not blocked **/
    public boolean valid(Card card) {
        if(card == null || card.getExpirationDate().isBefore(LocalDate.now()) || card.isBlocked()) return false;
        for(Card c : this.cards) {
            if(card.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean pay(Card card, int pin, double amount, String currency) {
        if(!valid(card) || !card.getType().equals("PHYSICAL")) return false;
        if(!card.checkPin(pin)) return false;

        for(Account a : this.accounts) {
            if(a.getCurrency().equals(currency) && a.getAmount() >= amount){
                a.subtractMoney(amount);
                return true;
            }
        }
        return false;
    }

    public boolean payOnline(Card card, int pin, double amount, String currency, String shopURL){
        if(!valid(card) || shopURL.substring(shopURL.length() - 4).equals(".biz")) return false;
        if(!card.checkPin(pin)) return false;

        if(card.getType().equals("VIRTUALONETIME")) {
            for(Account a : this.accounts) {
                if(a.getCurrency().equals(currency) && a.getAmount() >= amount){
                    a.subtractMoney(amount);
                    card.block();
                    return true;
                }
            }
        }
        else {
            for(Account a : this.accounts) {
                if(a.getCurrency().equals(currency) && a.getAmount() >= amount){
                    a.subtractMoney(amount);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addMoney(Account account, double amount){
        if(!this.exists(account)) return false;

        account.addMoney(amount);
        return true;
    }

    public boolean transferMoney(Account from, Account to, double amount) {
        if((!this.exists(from) || !this.exists(to)) || from.equals(to) || amount > from.getAmount()) return false;

        String fromCurrency = from.getCurrency();
        String toCurrency = to.getCurrency();

        if((fromCurrency.equals("BGN") && toCurrency.equals("BGN")) || (fromCurrency.equals("EUR") && toCurrency.equals("EUR"))) {
            from.subtractMoney(amount);
            to.addMoney(amount);
        }
        else if(fromCurrency.equals("BGN") && toCurrency.equals("EUR")) {
            from.subtractMoney(amount);
            to.addMoney(amount / 1.95583);
        }
        else if(fromCurrency.equals("EUR") && toCurrency.equals("BGN")) {
            from.subtractMoney(amount);
            to.addMoney(amount * 1.95583);
        }

        return true;
    }

    public double getTotalAmount() {
        double total = 0;
        for(Account a : this.accounts) {
            if(a.getCurrency().equals("BGN")){
                total += a.getAmount();
            }
            else {
                total += a.getAmount() * 1.95583;
            }
        }
        return total;
    }
}
