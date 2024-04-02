package edu.spring.weather_api.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.UUID;

@UtilityClass
public class CookieUtil {

    public void createSessionCookie(UUID sessionId, HttpServletResponse resp) {
        var cookie = new Cookie("session_id", sessionId.toString());
        cookie.setMaxAge(3600);
        resp.addCookie(cookie);
    }

    public void clearCookie(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("main_user");
        var cookie = new Cookie("session_id", null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public String getSessionIdFromCookie(HttpServletRequest req) {
        var cookies = req.getCookies();
        if (cookies == null)
            return null;
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("session_id"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
