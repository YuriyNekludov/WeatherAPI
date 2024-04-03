package edu.spring.weather_api.mapper.mapstruct;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.model.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "userId", source = "session.user.id")
    SessionDto toDto(Session session);
}
