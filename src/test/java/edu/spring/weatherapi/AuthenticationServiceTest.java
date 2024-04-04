package edu.spring.weatherapi;

import edu.spring.weather_api.WeatherApiApplication;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.mapper.UserMapper;
import edu.spring.weather_api.model.User;
import edu.spring.weather_api.repository.SessionRepository;
import edu.spring.weather_api.repository.UserRepository;
import edu.spring.weather_api.service.AuthenticationService;
import edu.spring.weather_api.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WeatherApiApplication.class)
@ActiveProfiles("test")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
public class AuthenticationServiceTest {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    final UserDtoReq USER_REQ = new UserDtoReq("testLogin", "123456", "123456");
    final UUID SESSION_ID = UUID.randomUUID();

    @Test
    @Rollback
    void registerUserShouldBeSuccess() {
        authenticationService.registerUser(USER_REQ, SESSION_ID);
        var user = userRepository.findByLogin(USER_REQ.login()).orElse(null);
        var session = sessionRepository.findById(SESSION_ID).orElse(null);
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(session).isNotNull(),
                () -> assertDoesNotThrow(() -> userService.getUserById(user.getId()))
        );
    }

    @Test
    @Rollback
    void registerUserWithNotUniqLoginShouldThrowException() {
        userRepository.save(User.builder()
                .login(USER_REQ.login())
                .password(USER_REQ.password())
                .build());
        assertThatThrownBy(() -> {
           authenticationService.registerUser(USER_REQ, SESSION_ID);
        }).isInstanceOf(Exception.class);
    }

    @Test
    @Rollback
    void loginUserShouldBeSuccess() {
        var user = User.builder()
                .login(USER_REQ.login())
                .password(BCrypt.hashpw(USER_REQ.password(), BCrypt.gensalt()))
                .build();
        userRepository.save(user);
        assertDoesNotThrow(() -> authenticationService.loginUser(USER_REQ, SESSION_ID));
    }

    @Test
    @Rollback
    void logoutUserShouldBeSuccess() {
        authenticationService.registerUser(USER_REQ, SESSION_ID);
        var user = userRepository.findByLogin(USER_REQ.login()).orElseThrow();
        authenticationService.logoutUser(userMapper.ToDto(user));
        assertThat(userRepository.findByLogin(USER_REQ.login()).orElse(null).getSession()).isNull();
        assertThat(sessionRepository.findById(SESSION_ID).orElse(null)).isNull();
    }
}
