package bg.sofia.uni.fmi.mjt.weather;

import bg.sofia.uni.fmi.mjt.weather.dto.WeatherCondition;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherData;
import bg.sofia.uni.fmi.mjt.weather.dto.WeatherForecast;
import bg.sofia.uni.fmi.mjt.weather.exceptions.LocationNotFoundException;
import bg.sofia.uni.fmi.mjt.weather.exceptions.WeatherForecastClientException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherForecastClientTest {

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private HttpResponse<String> httpResponseMock;

    private WeatherForecastClient client;

    @Before
    public void setUp() {
        client = new WeatherForecastClient(httpClientMock);
    }

    @Test(expected = LocationNotFoundException.class)
    public void testGetForecastLocationNotFound()
            throws IOException, InterruptedException, WeatherForecastClientException {
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);
        when(httpResponseMock.statusCode()).thenReturn(404);
        client.getForecast("blabla");
    }

    @Test(expected = WeatherForecastClientException.class)
    public void testGetForecastWeatherForecastClientException()
            throws IOException, InterruptedException, WeatherForecastClientException {
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenThrow(IOException.class);
        client.getForecast("Sofia");
    }

    @Test
    public void testGetForecastStandard() throws IOException, InterruptedException, WeatherForecastClientException {
        when(httpClientMock.send(Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any())).thenReturn(httpResponseMock);

        String json = "{\"weather\":[{\"description\":\"ясно небе\"}],"
                + "\"main\":{\"temp\":-6,\"feels_like\":-11.22}}";

        when(httpResponseMock.body()).thenReturn(json);

        WeatherCondition[] weather = new WeatherCondition[1];
        weather[0] = new WeatherCondition("ясно небе");

        WeatherData main = new WeatherData(-6, -11.22);

        WeatherForecast expected = new WeatherForecast(weather, main);

        assertEquals("Wrong weather data retrieval.", expected, client.getForecast("Нова Загора"));
    }
}
