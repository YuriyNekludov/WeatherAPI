package edu.spring.weather_api.exception.exception_strategy.impl;

import edu.spring.weather_api.exception.exception_strategy.ExceptionHandlingStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class LocationAlreadyAddedExceptionHandler implements ExceptionHandlingStrategy {

    @Override
    public String handleException(RedirectAttributes redirectAttributes, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_CONFLICT);
        redirectAttributes.addAttribute("message", "Вы уже добавили данную локацию в свой профиль.");
        return "redirect:/error";
    }
}
