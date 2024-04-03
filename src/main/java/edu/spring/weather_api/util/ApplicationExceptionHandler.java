package edu.spring.weather_api.util;

import edu.spring.weather_api.exception.exception_strategy.ExceptionHandlingStrategyFactory;
import edu.spring.weather_api.exception.exception_strategy.impl.AnotherExceptionHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@ControllerAdvice
public class ApplicationExceptionHandler {

    ExceptionHandlingStrategyFactory exceptionStrategyFactory;

    @ExceptionHandler
    public String handleException(RedirectAttributes redirectAttributes,
                                  HttpServletResponse resp,
                                  Exception e) {
        var exceptionStrategy = exceptionStrategyFactory.getExceptionStrategy(e);
        if (exceptionStrategy instanceof AnotherExceptionHandler)
            log.warn("Something's wrong: {}", e.getStackTrace());
        return exceptionStrategy.handleException(redirectAttributes, resp);
    }
}
