package bg.sofia.uni.fmi.mjt.netflix;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Episode;
import bg.sofia.uni.fmi.mjt.netflix.content.Movie;
import bg.sofia.uni.fmi.mjt.netflix.content.Series;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;
import bg.sofia.uni.fmi.mjt.netflix.platform.Netflix;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Episode e1 = new Episode("1vi", 25);
        Episode e2 = new Episode("2ri", 28);
        Episode e3 = new Episode("3ti", 26);
        Episode[] eps1 = {e1,e2,e3};

	    Streamable s1 = new Movie("film1", Genre.ACTION, PgRating.PG13, 116);
        Streamable s2 = new Movie("film2", Genre.COMEDY, PgRating.G, 95);
        Streamable s3 = new Movie("film3", Genre.HORROR, PgRating.NC17, 102);
        Streamable s4 = new Series("serial1", Genre.COMEDY, PgRating.G, eps1);

        Streamable[] content = {s1,s2,s3,s4};

        LocalDateTime birthday1 = LocalDateTime.of(1998, 5,1,22,5);
        LocalDateTime birthday2 = LocalDateTime.of(2004, 5,1,22,5);
        LocalDateTime birthday3 = LocalDateTime.of(2008, 5,1,22,5);

        Account acc1 = new Account("Simeon", birthday1);
        Account acc2 = new Account("Dragan", birthday2);
        Account acc3 = new Account("Petkan", birthday3);

        Account[] accs = {acc1, acc2, acc3};

        Netflix nf = new Netflix(accs, content);
        System.out.println("Total watched time by users: " + nf.totalWatchedTimeByUsers());

        System.out.println("Total watched time by users: " + nf.totalWatchedTimeByUsers());
        System.out.println("Most watched show: " +
                nf.mostViewed().getTitle());
        System.out.println("Find that: " + nf.findByName("film1").getTitle());

    }
}
