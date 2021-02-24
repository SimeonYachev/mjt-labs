package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import bg.sofia.uni.fmi.mjt.weather.exceptions.WeatherForecastClientException;
import bg.sofia.uni.fmi.mjt.weather.exceptions.LocationNotFoundException;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherForecastClient {

    private static final String API_URI = "http://api.openweathermap.org";
    private static final String API_METHOD = "/data/2.5/weather?q=";
    private static final String WEATHER_PARAMS = "&units=metric&lang=bg&appid=";
    private static final String WEATHER_API_KEY = "ba782475edc1b88fda23712a409ad543";

    private HttpClient client;

    public WeatherForecastClient(HttpClient weatherHttpClient) {
        client = weatherHttpClient;
    }

    public WeatherForecast getForecast(String city) throws WeatherForecastClientException {
        if (city.contains(" ")) {
            city = city.replace(" ", "%20");
        }

        String url = API_URI + API_METHOD + city + WEATHER_PARAMS + WEATHER_API_KEY;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                throw new LocationNotFoundException();
            }

            Gson gson = new Gson();
            String json = response.body();

            return gson.fromJson(json, WeatherForecast.class);

        } catch (IOException | InterruptedException e) {
            throw new WeatherForecastClientException(e.getMessage(), e);
        }
    }

}