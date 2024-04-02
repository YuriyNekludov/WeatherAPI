package edu.spring.weather_api.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CoordinatesDto(BigDecimal latitude,
                             BigDecimal longitude,
                             String name,
                             String state) {
}
