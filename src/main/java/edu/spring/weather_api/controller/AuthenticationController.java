package edu.spring.weather_api.controller;

import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.service.AuthenticationService;
import edu.spring.weather_api.dto.user.UserDtoReq;
import edu.spring.weather_api.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @GetMapping("/login")
    public String login(@ModelAttribute("user") UserDtoReq userDtoReq) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String doLogin(@ModelAttribute("user") @Valid UserDtoReq userDtoReq,
                          BindingResult bindingResult,
                          HttpServletResponse resp) {
        if (bindingResult.hasErrors())
            return "authentication/login";
        UUID sessionId = UUID.randomUUID();
        authenticationService.loginUser(userDtoReq, sessionId);
        CookieUtil.createSessionCookie(sessionId, resp);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") UserDtoReq userDtoReq) {
        return "authentication/registration";
    }

    @PostMapping("/registration")
    public String doRegistration(@ModelAttribute("user") @Valid UserDtoReq userDtoReq,
                                 BindingResult bindingResult,
                                 HttpServletResponse resp) {
        if (bindingResult.hasErrors())
            return "authentication/registration";
        UUID sessionId = UUID.randomUUID();
        authenticationService.registerUser(userDtoReq, sessionId);
        CookieUtil.createSessionCookie(sessionId, resp);
        return "redirect:/";
    }

    @DeleteMapping("/logout")
    public String doLogout(HttpServletRequest req, HttpServletResponse resp) {
        var userDto = (UserDto) req.getSession().getAttribute("main_user");
        authenticationService.logoutUser(userDto);
        CookieUtil.clearCookie(req, resp);
        return "redirect:/";
    }
}
