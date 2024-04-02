package edu.spring.weather_api.dto.location;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LocationDtoResp(BigDecimal latitude,
                              BigDecimal longitude,
                              String weatherDescription,
                              BigDecimal temperature,
                              BigDecimal feelsLike,
                              String country,
                              String name,
                              String state,
                              String icon) {
}
