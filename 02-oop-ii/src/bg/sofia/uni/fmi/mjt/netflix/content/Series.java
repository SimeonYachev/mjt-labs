package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public class Series extends StreamableContent {
    private Episode[] episodes;

    public Series(String name, Genre genre, PgRating rating, Episode[] episodes) {
        super(name, genre, rating);
        this.episodes = episodes;
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public int getDuration() {
        int sum = 0;
        for(Episode e : episodes) {
            sum += e.duration();
        }
        return sum;
    }

    @Override
    public Genre getGenre() {
        return super.getGenre();
    }

    @Override
    public PgRating getRating() {
        return super.getRating();
    }
}
