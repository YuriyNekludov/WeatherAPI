package edu.spring.weather_api.util;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Log4j2
public class ApplicationExceptionHandler {

    @ExceptionHandler
    public String handleException(RedirectAttributes redirectAttributes,
                                  HttpServletResponse resp,
                                  Exception e) {
        switch (e.getClass().getSimpleName()) {
            case "UserNotFoundException" -> {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                redirectAttributes.addAttribute("message", "Пользователь с таким логином не найден.");
                return "redirect:/login";
            }
            case "IncorrectPasswordException" -> {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                redirectAttributes.addAttribute("message", "Пароль введен не правильно.");
                return "redirect:/login";
            }
            case "PasswordMismatchException" -> {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                redirectAttributes.addAttribute("message", "Введенные пароли должны совпадать.");
                return "redirect:/registration";
            }
            case "UserAlreadyCreatedException" -> {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                redirectAttributes.addAttribute("message", "Пользователь с таким логином уже существует.");
                return "redirect:/registration";
            }
            case "LocationAlreadyAddedException" -> {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
                redirectAttributes.addAttribute("message", "Вы уже добавили данную локацию в свой профиль");
                return "redirect:/error";
            }
            case "OpenWeatherApiNotUnavailableException" -> {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                redirectAttributes.addAttribute("message", "Погодный сервис временно недоступен");
                return "redirect:/error";
            }
            default -> {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                log.warn("Something's wrong: {}", e.getStackTrace());
                redirectAttributes.addAttribute("message", "Что-то пошло не так...");
                return "redirect:/error";
            }
        }
    }
}
