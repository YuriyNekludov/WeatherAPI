package edu.spring.weather_api.mapper;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.model.Session;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {
    public SessionDto dtoMapFrom(Session session) {
        if (session == null)
            return null;
        return SessionDto.builder()
                .id(session.getId())
                .userId(session.getUser().getId())
                .expiresAt(session.getExpiresAt())
                .build();
    }
}
