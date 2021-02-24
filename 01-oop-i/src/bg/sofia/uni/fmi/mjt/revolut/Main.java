package bg.sofia.uni.fmi.mjt.revolut;

import bg.sofia.uni.fmi.mjt.revolut.account.Account;
import bg.sofia.uni.fmi.mjt.revolut.account.BGNAccount;
import bg.sofia.uni.fmi.mjt.revolut.account.EURAccount;
import bg.sofia.uni.fmi.mjt.revolut.card.Card;
import bg.sofia.uni.fmi.mjt.revolut.card.PhysicalCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualOneTimeCard;
import bg.sofia.uni.fmi.mjt.revolut.card.VirtualPermanentCard;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        BGNAccount bg1 = new BGNAccount("1234", 69.5);
        BGNAccount bg2 = new BGNAccount("2345", 15.0);
        BGNAccount bg3 = new BGNAccount("3456", 144.69);
        EURAccount eu1 = new EURAccount("abcd", 33.3);
        EURAccount eu2 = new EURAccount("bcde", 18.9);
        EURAccount eu3 = new EURAccount("cdef", 54.3);

        PhysicalCard pc1 = new PhysicalCard("1111", 1111, LocalDate.of(2020,5,1));
        PhysicalCard pc2 = new PhysicalCard("2222", 2222, LocalDate.of(2020,6,1));
        VirtualPermanentCard vpc1 = new VirtualPermanentCard("3333", 3333, LocalDate.of(2020,7,1));
        VirtualPermanentCard vpc2 = new VirtualPermanentCard("4444", 4444, LocalDate.of(2020,8,1));
        VirtualOneTimeCard votc1 = new VirtualOneTimeCard("5555", 5555, LocalDate.of(2021,9,1));
        VirtualOneTimeCard votc2 = new VirtualOneTimeCard("6666", 6666, LocalDate.of(2020,12,22));

        Account[] accounts = {bg1,bg2,bg3,eu1,eu2,eu3};
        Card[] cards = {pc1,pc2,vpc1,vpc2,votc1,votc2};

        Revolut rev = new Revolut(accounts,cards);

        System.out.println(bg1.getAmount());
        System.out.println(bg2.getAmount());
        System.out.println(bg3.getAmount());
        rev.payOnline(vpc1,3333,5.5, "BGN", "sait4e.com");
        System.out.println(bg1.getAmount());
        System.out.println(bg2.getAmount());
        System.out.println(bg3.getAmount());

    }
}
