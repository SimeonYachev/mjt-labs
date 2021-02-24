package bg.sofia.uni.fmi.mjt.weather.exceptions;

public class LocationNotFoundException extends WeatherForecastClientException {
    public LocationNotFoundException() {
        super("City not found.");
    }
}
