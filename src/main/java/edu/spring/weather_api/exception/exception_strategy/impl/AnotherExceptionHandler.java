package edu.spring.weather_api.exception.exception_strategy.impl;

import edu.spring.weather_api.exception.exception_strategy.ExceptionHandlingStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AnotherExceptionHandler implements ExceptionHandlingStrategy {

    @Override
    public String handleException(RedirectAttributes redirectAttributes, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        redirectAttributes.addAttribute("message", "Что-то пошло не так...");
        return "redirect:/error";
    }
}
