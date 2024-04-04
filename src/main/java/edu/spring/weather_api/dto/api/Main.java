package edu.spring.weather_api.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Main(
        @JsonProperty("temp")
        BigDecimal temperature,
        @JsonProperty("feels_like")
        BigDecimal feelsLike) {
}
