package edu.spring.weather_api.controller;

import edu.spring.weather_api.dto.location.LocationDtoReq;
import edu.spring.weather_api.service.OpenWeatherService;
import edu.spring.weather_api.service.LocationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/search")
public class SearchLocationController {

    OpenWeatherService openWeatherService;
    LocationService locationService;

    @GetMapping()
    public String searchLocationsByName(@RequestParam(value = "name", required = false) String name, Model model) {
        var locations = openWeatherService.getWeatherDataByCityName(name);
        model.addAttribute("locations", locations);
        return "location/search";
    }

    @PostMapping("/add")
    public String addLocationToUserProfile(@ModelAttribute LocationDtoReq userLocation) {
        locationService.saveLocation(userLocation);
        return "redirect:/profile";
    }
}
