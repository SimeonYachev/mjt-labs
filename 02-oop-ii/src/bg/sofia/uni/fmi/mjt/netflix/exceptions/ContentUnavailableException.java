package bg.sofia.uni.fmi.mjt.netflix.exceptions;

public class ContentUnavailableException extends Exception {
    public ContentUnavailableException() {
        super("This content is age restricted.");
    }
}
