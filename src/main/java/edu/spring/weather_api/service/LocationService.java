package edu.spring.weather_api.service;

import edu.spring.weather_api.dto.location.LocationDtoReq;
import edu.spring.weather_api.dto.location.LocationDtoResp;
import edu.spring.weather_api.dto.user.UserDto;
import edu.spring.weather_api.exception.LocationAlreadyAddedException;
import edu.spring.weather_api.exception.LocationNotFoundException;
import edu.spring.weather_api.exception.UserNotFoundException;
import edu.spring.weather_api.mapper.LocationWeatherMapper;
import edu.spring.weather_api.model.Location;
import edu.spring.weather_api.repository.LocationRepository;
import edu.spring.weather_api.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Collections.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    OpenWeatherService openWeatherService;
    UserRepository userRepository;
    LocationRepository locationRepository;
    LocationWeatherMapper locationWeatherMapper;

    @Transactional
    public void saveLocation(LocationDtoReq userLocation) {
        var location = Location.builder()
                .name(userLocation.name())
                .longitude(userLocation.longitude())
                .latitude(userLocation.latitude())
                .state(userLocation.state())
                .build();
        var user = userRepository.findById(userLocation.userId()).orElseThrow(UserNotFoundException::new);
        location.setUser(user);
        try {
            locationRepository.save(location);
        } catch (Exception e) {
            throw new LocationAlreadyAddedException();
        }
    }

    public List<LocationDtoResp> getAllUserLocations(UserDto userDto) {
        var locations = locationRepository.findAllByUserId(userDto.id());
        if (locations.isEmpty())
            return emptyList();
        return locations.stream()
                .map(locationWeatherMapper::locationInfoFromLocation)
                .map(openWeatherService::getLocationAndWeatherDataByInfo)
                .toList();
    }

    @Transactional
    public void removeLocationFromUser(UserDto userDto, LocationDtoReq userLocation) {
        var location = locationRepository.findByLatitudeAndLongitudeAndUserId(
                        userLocation.latitude(),
                        userLocation.longitude(),
                        userDto.id())
                .orElseThrow(LocationNotFoundException::new);
        locationRepository.delete(location);
    }
}
