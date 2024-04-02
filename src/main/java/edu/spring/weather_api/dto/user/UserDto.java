package edu.spring.weather_api.dto.user;

import lombok.Builder;

@Builder
public record UserDto(Long id,
                      String login) {
}
