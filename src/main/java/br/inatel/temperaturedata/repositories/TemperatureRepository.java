package br.inatel.temperaturedata.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.inatel.temperaturedata.entities.Temperature;

/**
 * 
 * @author rafael.rocha
 * 
 * Repository interface that extends Jpa Repository to work with requisitions.
 */
public interface TemperatureRepository extends JpaRepository<Temperature, String> {

	Optional<Temperature> findByDateAndCityId(LocalDate date, Long id);

	List<Temperature> findAllByCityId(Long id);

	Optional<Temperature> findTemperatureByDateAndCityId(LocalDate date, Long id);

}
