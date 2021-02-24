package bg.sofia.uni.fmi.mjt.netflix.exceptions;

public class ContentNotFoundException extends RuntimeException {
    public ContentNotFoundException() {
        super("We can't find what you are looking for. :(");
    }
}
