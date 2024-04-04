package edu.spring.weather_api.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Weather(String description,
                      String icon) {
}
