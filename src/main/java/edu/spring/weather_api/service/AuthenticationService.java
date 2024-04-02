package edu.spring.weather_api.service;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.mapper.SessionMapper;
import edu.spring.weather_api.mapper.UserMapper;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.exception.IncorrectPasswordException;
import edu.spring.weather_api.exception.PasswordMismatchException;
import edu.spring.weather_api.model.Session;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class AuthenticationService {

    UserService userService;
    SessionService sessionService;
    DtoConverterService dtoConverterService;

    public void registerUser(UserDtoReq userDtoReq, UUID sessionId) {
        if (!userDtoReq.password().equals(userDtoReq.repeatPassword()))
            throw new PasswordMismatchException();
        var user = dtoConverterService.getUserFromDto(userDtoReq);
        var session = Session.builder()
                .id(sessionId)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .build();
        session.setUser(user);
        userService.saveUser(user);
        log.info("User {} was registered successfully.", user.getLogin());
    }

    public void loginUser(UserDtoReq userDtoReq, UUID sessionId) {
        var user = userService.getUserByLogin(userDtoReq.login());
        if (!BCrypt.checkpw(userDtoReq.password(), user.getPassword()))
            throw new IncorrectPasswordException();
        if (user.getSession() != null) {
            sessionService.deleteSession(user.getSession());
            user.setSession(null);
        }
        var session = Session.builder()
                .id(sessionId)
                .expiresAt(LocalDateTime.now().plusHours(1))
                .build();
        session.setUser(user);
        log.info("User {} was login successfully.", user.getLogin());
    }

    @CacheEvict(value = "users", key = "#id")
    public void logoutUser(Long id) {
        var user = userService.getUserById(id);
        if (user.getSession() == null)
            return;
        sessionService.deleteSession(user.getSession());
        user.setSession(null);
        log.info("User {} was logout successfully.", user.getLogin());
    }
}
