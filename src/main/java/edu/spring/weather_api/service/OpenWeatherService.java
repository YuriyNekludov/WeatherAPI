package edu.spring.weather_api.service;

import com.google.gson.JsonParser;
import edu.spring.weather_api.dto.CoordinatesDto;
import edu.spring.weather_api.dto.location.LocationDtoResp;
import edu.spring.weather_api.exception.LocationNotFoundException;
import edu.spring.weather_api.exception.OpenWeatherApiNotUnavailableException;
import edu.spring.weather_api.mapper.LocationWeatherMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OpenWeatherService {

    LocationWeatherMapper locationWeatherMapper;

    @Value("${weather.appid}")
    @NonFinal
    String WEATHER_APP_ID;

    @Value("${weather.url.coordinates}")
    @NonFinal
    String WEATHER_URL_COORDINATES;

    @Value("${weather.url.cities}")
    @NonFinal
    String WEATHER_URL_CITIES;

    public List<LocationDtoResp> getWeatherDataByCityName(String name) {
        return findLocationsCoordinatesByName(name).stream()
                .map(this::getWeatherDataByCoordinates)
                .toList();
    }

    public LocationDtoResp getWeatherDataByCoordinates(CoordinatesDto coordinates) {
        return findLocationAndWeatherByCoordinates(coordinates);
    }

    private List<CoordinatesDto> findLocationsCoordinatesByName(String name) {
        var restTemplate = new RestTemplate();
        var urlEncoded = UriComponentsBuilder.fromHttpUrl(WEATHER_URL_CITIES)
                .queryParam("q", name)
                .queryParam("appid", WEATHER_APP_ID)
                .queryParam("limit", 0)
                .encode()
                .toUriString();
        var uri = URI.create(urlEncoded);
        var response = restTemplate.getForEntity(uri, String.class);
        if (response.getStatusCode().isError())
            throw new LocationNotFoundException();
        var jsonArray = JsonParser.parseString(response.getBody()).getAsJsonArray();
        return locationWeatherMapper.parseJsonArrayToCoordinatesList(jsonArray);
    }

    private LocationDtoResp findLocationAndWeatherByCoordinates(CoordinatesDto coordinates) {
        var restTemplate = new RestTemplate();
        var urlEncoded = UriComponentsBuilder.fromHttpUrl(WEATHER_URL_COORDINATES)
                .queryParam("lon", coordinates.longitude())
                .queryParam("lat", coordinates.latitude())
                .queryParam("units", "metric")
                .queryParam("appid", WEATHER_APP_ID)
                .encode()
                .toUriString();
        var uri = URI.create(urlEncoded);
        var response = restTemplate.getForEntity(uri, String.class);
        if (response.getStatusCode().isError())
            throw new OpenWeatherApiNotUnavailableException();
        var jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
        return locationWeatherMapper.parseJsonObjectToLocationDto(jsonObject, coordinates);
    }
}