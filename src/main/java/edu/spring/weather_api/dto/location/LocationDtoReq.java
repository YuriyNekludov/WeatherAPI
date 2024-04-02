package edu.spring.weather_api.dto.location;

import java.math.BigDecimal;

public record LocationDtoReq(String name,
                             BigDecimal latitude,
                             BigDecimal longitude,
                             String state,
                             Long userId) {
}
