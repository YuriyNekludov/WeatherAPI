package edu.spring.weather_api.mapper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edu.spring.weather_api.dto.CoordinatesDto;
import edu.spring.weather_api.dto.location.LocationDtoResp;
import edu.spring.weather_api.model.Location;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LocationWeatherMapper {

    public CoordinatesDto coordinatesDtoFromLocation(Location location) {
        if (location == null)
            return null;
        return CoordinatesDto.builder()
                .longitude(location.getLongitude())
                .latitude(location.getLatitude())
                .name(location.getName())
                .state(location.getState())
                .build();
    }

    public LocationDtoResp parseJsonObjectToLocationDto(JsonObject jsonObject, CoordinatesDto coordinates) {
        var coord = jsonObject.getAsJsonObject("coord");
        var weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        var description = weather.get("description").getAsString();
        var icon = weather.get("icon").getAsString();
        var temperature = jsonObject.getAsJsonObject("main").get("temp").getAsBigDecimal();
        var feelsLike = jsonObject.getAsJsonObject("main").get("feels_like").getAsBigDecimal();
        var country = jsonObject.getAsJsonObject("sys").get("country").getAsString();
        return LocationDtoResp.builder()
                .latitude(coord.get("lat").getAsBigDecimal())
                .longitude(coord.get("lon").getAsBigDecimal())
                .weatherDescription(description)
                .temperature(temperature)
                .feelsLike(feelsLike)
                .country(country)
                .name(coordinates.name())
                .state(coordinates.state())
                .icon(icon)
                .build();
    }

    public List<CoordinatesDto> parseJsonArrayToCoordinatesList(JsonArray jsonArray) {
        var jsonList = jsonArray.asList();
        return jsonList.stream()
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> {
                    CoordinatesDto.CoordinatesDtoBuilder builder = CoordinatesDto.builder();
                    builder.latitude(jsonObject.get("lat").getAsBigDecimal());
                    builder.longitude(jsonObject.get("lon").getAsBigDecimal());
                    builder.name(jsonObject.get("name").getAsString());
                    if (jsonObject.has("state"))
                        builder.state(jsonObject.get("state").getAsString());
                    else
                        builder.state("Unknown");
                    return builder.build();
                })
                .toList();
    }
}
