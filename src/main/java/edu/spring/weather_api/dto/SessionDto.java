package edu.spring.weather_api.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record SessionDto(UUID id,
                         Long userId,
                         LocalDateTime expiresAt) {
}
