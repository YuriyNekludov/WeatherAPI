package edu.spring.weather_api.filter;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.service.AuthenticationService;
import edu.spring.weather_api.service.DtoConverterService;
import edu.spring.weather_api.util.CookieUtil;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@WebFilter("/*")
public class SessionValidationFilter implements Filter {

    AuthenticationService authenticationService;
    DtoConverterService dtoConverterService;
    List<String> urlPartsForRedirect = List.of("profile", "add", "delete", "logout");

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        var req = (HttpServletRequest) request;
        var resp = (HttpServletResponse) response;
        var uri = req.getRequestURI();
        if (isStaticResource(uri)) {
            chain.doFilter(request, response);
            return;
        }
        String sessionId = CookieUtil.getSessionIdFromCookie(req);
        if (sessionId == null) {
            var lastPartUri = uri.substring(uri.lastIndexOf("/") + 1);
            req.getSession().removeAttribute("main_user");
            if (urlPartsForRedirect.contains(lastPartUri)) {
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        } else {
            var sessionDto = dtoConverterService.getSessionDtoById(UUID.fromString(sessionId));
            if (sessionDto != null) {
                if (isSessionEnded(sessionDto)) {
                    var userDto = (UserDto) req.getSession().getAttribute("main_user");
                    authenticationService.logoutUser(userDto);
                    CookieUtil.clearCookie(req, resp);
                    resp.sendRedirect(req.getContextPath() + "/");
                    return;
                } else {
                    if (req.getSession().getAttribute("main_user") == null)
                        req.getSession().setAttribute("main_user", dtoConverterService.getUserDtoById(sessionDto.userId()));
                }
            } else
                req.getSession().removeAttribute("main_user");
        }
        chain.doFilter(request, response);
    }

    private boolean isStaticResource(String reqUri) {
        return reqUri.endsWith(".css") || reqUri.endsWith(".js") || reqUri.endsWith(".gif") || reqUri.endsWith(".svg")
                || reqUri.endsWith(".png");
    }

    private boolean isSessionEnded(SessionDto sessionDto) {
        return sessionDto.expiresAt().isBefore(LocalDateTime.now());
    }
}
