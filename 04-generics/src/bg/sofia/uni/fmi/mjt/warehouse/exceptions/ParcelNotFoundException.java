package bg.sofia.uni.fmi.mjt.warehouse.exceptions;

public class ParcelNotFoundException extends RuntimeException {
    public ParcelNotFoundException() {
        super("There is no such parcel.");
    }
}
