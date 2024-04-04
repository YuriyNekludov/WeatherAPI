package edu.spring.weather_api.mapper;

import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto ToDto(User user);

    User toEntity(UserDtoReq userDtoReq);
}
