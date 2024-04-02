package edu.spring.weather_api.dto.user;

import lombok.Builder;

@Builder
public record UserDtoResp(Long id,
                          String login) {
}
