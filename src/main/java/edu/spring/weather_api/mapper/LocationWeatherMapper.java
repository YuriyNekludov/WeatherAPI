package edu.spring.weather_api.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.spring.weather_api.dto.api.LocationWeather;
import edu.spring.weather_api.dto.api.LocationInfo;
import edu.spring.weather_api.dto.location.LocationDtoResp;
import edu.spring.weather_api.exception.ParseJsonException;
import edu.spring.weather_api.model.Location;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LocationWeatherMapper {

    public List<LocationInfo> getLocationInfoFromResponse(String response) {
        var objectMapper = new ObjectMapper();
        try {
            LocationInfo[] locationInfo = objectMapper.readValue(response, LocationInfo[].class);
            return Arrays.stream(locationInfo).toList();
        } catch (JsonProcessingException e) {
            throw new ParseJsonException();
        }
    }

    public LocationWeather getLocationWeatherFromResponse(String response) {
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, LocationWeather.class);
        } catch (JsonProcessingException e) {
            throw new ParseJsonException();
        }
    }

    public LocationDtoResp getLocationDtoResp(LocationWeather locationWeather, LocationInfo locationInfo) {
        return LocationDtoResp.builder()
                .latitude(locationInfo.latitude())
                .longitude(locationInfo.longitude())
                .country(locationWeather.sys().country())
                .name(locationInfo.name())
                .weatherDescription(locationWeather.weather()[0].description())
                .temperature(locationWeather.main().temperature())
                .feelsLike(locationWeather.main().feelsLike())
                .state(locationInfo.state())
                .icon(locationWeather.weather()[0].icon())
                .build();
    }

    public LocationInfo locationInfoFromLocation(Location location) {
        return LocationInfo.builder()
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .name(location.getName())
                .state(location.getState())
                .build();
    }
}
