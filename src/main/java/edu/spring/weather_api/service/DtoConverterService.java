package edu.spring.weather_api.service;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.mapper.SessionMapper;
import edu.spring.weather_api.mapper.UserMapper;
import edu.spring.weather_api.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DtoConverterService {

    SessionService sessionService;
    UserService userService;
    SessionMapper sessionMapper;
    UserMapper userMapper;

    @Transactional(readOnly = true)
    public SessionDto getSessionDtoById(UUID sessionId) {
        return sessionMapper.dtoMapFrom(sessionService.getById(sessionId));
    }

    @Transactional(readOnly = true)
    public UserDto getUserDtoById(Long id) {
        return userMapper.dtoMapFrom(userService.getUserById(id));
    }

    public User getUserFromDto(UserDtoReq userDtoReq) {
        return userMapper.entityMapFrom(userDtoReq);
    }
}
