package edu.spring.weather_api.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDtoReq(
        @NotBlank(message = "Поле не должно быть пустым и содержать пробелов")
        @Size(min = 5, max = 30, message = "Логин должен состоять из 5-30 символов")
        String login,

        @NotEmpty(message = "Поле не должно быть пустым")
        @Size(min = 6, max = 20, message = "Пароль должен состоять из 6-20 символов")
        @Pattern(regexp = "[\\dA-Za-zА-Яа-я]+", message = "пароль должен состоять только из цифр и букв")
        String password,
        String repeatPassword
) {
}
