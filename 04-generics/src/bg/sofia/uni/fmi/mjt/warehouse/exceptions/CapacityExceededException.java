package bg.sofia.uni.fmi.mjt.warehouse.exceptions;

public class CapacityExceededException extends RuntimeException {
    public CapacityExceededException() {
        super("The warehouse is full.");
    }
}
