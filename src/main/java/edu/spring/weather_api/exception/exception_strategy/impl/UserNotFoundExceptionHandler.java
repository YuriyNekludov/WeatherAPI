package edu.spring.weather_api.exception.exception_strategy.impl;

import edu.spring.weather_api.exception.exception_strategy.ExceptionHandlingStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class UserNotFoundExceptionHandler implements ExceptionHandlingStrategy {

    @Override
    public String handleException(RedirectAttributes redirectAttributes, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        redirectAttributes.addAttribute("message", "Пользователь с таким логином не найден.");
        return "redirect:/login";
    }
}
