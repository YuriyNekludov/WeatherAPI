package edu.spring.weather_api.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LocationInfo(
        @JsonProperty("lat")
        BigDecimal latitude,
        @JsonProperty("lon")
        BigDecimal longitude,
        String name,
        String state) {
}


