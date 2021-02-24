package bg.sofia.uni.fmi.mjt.netflix.content;

import bg.sofia.uni.fmi.mjt.netflix.content.enums.Genre;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;

public abstract class StreamableContent implements Streamable {
    private String name;
    private Genre genre;
    private PgRating rating;
    private static int viewsCount = 0;

    public StreamableContent(String name, Genre genre, PgRating rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    @Override
    public String getTitle() {
        return this.name;
    }

    @Override
    public abstract int getDuration();

    public Genre getGenre() {
        return this.genre;
    }

    @Override
    public PgRating getRating() {
        return this.rating;
    }

}
