package edu.spring.weather_api.mapper.mapstruct;

import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto TorDto(User user);

    User toEntity(UserDtoReq userDtoReq);
}
