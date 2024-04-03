package edu.spring.weather_api.exception.exception_strategy;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ExceptionHandlingStrategy {

    String handleException(RedirectAttributes redirectAttributes, HttpServletResponse resp);
}
