package edu.spring.weather_api.mapper;

import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.dto.user.UserDtoResp;
import edu.spring.weather_api.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDtoResp dtoMapFrom(User entity) {
        if (entity == null)
            return null;
        return UserDtoResp.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .build();
    }

    public User entityMapFrom(UserDtoReq dtoReq) {
        if (dtoReq == null)
            return null;
        var cryptPassword = BCrypt.hashpw(dtoReq.password(), BCrypt.gensalt());
        return User.builder()
                .login(dtoReq.login())
                .password(cryptPassword)
                .build();
    }
}