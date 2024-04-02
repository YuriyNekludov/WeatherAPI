package edu.spring.weather_api.repository;

import edu.spring.weather_api.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAllByUserId(Long id);

    Optional<Location> findByLatitudeAndLongitudeAndUserId(BigDecimal latitude, BigDecimal longitude, Long userId);
}
