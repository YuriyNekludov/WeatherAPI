package edu.spring.weather_api.controller;

import edu.spring.weather_api.dto.location.LocationDtoReq;
import edu.spring.weather_api.dto.user.UserDtoResp;
import edu.spring.weather_api.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequestMapping("/profile")
public class ProfileController {

    LocationService locationService;

    @GetMapping()
    public String getUserProfile(Model model, HttpServletRequest req) {
        var user = (UserDtoResp) req.getSession().getAttribute("main_user");
        if (user == null)
            return "redirect:/";
        model.addAttribute("locations", locationService.getAllUserLocations(user.id()));
        return "profile/profile";
    }

    @DeleteMapping("/delete")
    public String removeLocationFromProfile(@ModelAttribute LocationDtoReq userLocation,
                                            HttpServletRequest req) {
        var user = (UserDtoResp) req.getSession().getAttribute("main_user");
        locationService.removeLocationFromUser(user.id(), userLocation);
        return "redirect:/profile";
    }
}
