package br.inatel.temperaturedata.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.temperaturedata.entities.City;

/**
 * 
 * @author rafael.rocha 
 * Repository interface that extends Jpa Repository to work
 * with requisitions.
 */
public interface CityRepository extends JpaRepository<City, String> {

	Optional<City> findByCityName(String cityName);

	Optional<City> findByLatitudeAndLongitude(Double latitude, Double longitude);

}
