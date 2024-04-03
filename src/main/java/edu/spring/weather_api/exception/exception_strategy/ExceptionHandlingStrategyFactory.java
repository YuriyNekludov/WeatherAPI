package edu.spring.weather_api.exception.exception_strategy;

import edu.spring.weather_api.exception.*;
import edu.spring.weather_api.exception.exception_strategy.impl.*;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlingStrategyFactory {

    public ExceptionHandlingStrategy getExceptionStrategy(Exception e) {
        if (e instanceof UserNotFoundException)
            return new UserNotFoundExceptionHandler();
        else if (e instanceof IncorrectPasswordException)
            return new IncorrectPasswordExceptionHandler();
        else if (e instanceof LocationAlreadyAddedException)
            return new LocationAlreadyAddedExceptionHandler();
        else if (e instanceof OpenWeatherApiNotUnavailableException)
            return new OpenWeatherApiNotUnavailableExceptionHandler();
        else if (e instanceof PasswordMismatchException)
            return new PasswordMismatchExceptionHandler();
        else if (e instanceof UserAlreadyCreatedException)
            return new UserAlreadyCreatedExceptionHandler();
        else
            return new AnotherExceptionHandler();
    }
}
