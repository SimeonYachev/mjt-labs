package bg.sofia.uni.fmi.mjt.smartcity.hub;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException() {
        super("Can't find such a device.");
    }
}
