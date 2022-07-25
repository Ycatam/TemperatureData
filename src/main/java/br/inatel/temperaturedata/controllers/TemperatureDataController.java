package br.inatel.temperaturedata.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.temperaturedata.dto.CityTemperatureDto;
import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.entities.Temperature;
import br.inatel.temperaturedata.services.CityTemperatureService;

/**
 * 
 * @author rafael.rocha
 *
 * This is the main controller to handle all requisitions to store
 * temperatures and get DB information based in specifics endpoints.
 */

@RestController
@RequestMapping("/temperatures")
public class TemperatureDataController {

	private final CityTemperatureService cityTempService;

	public TemperatureDataController(CityTemperatureService cityTempService) {
		this.cityTempService = cityTempService;
	}

	/**
	 * This method receive a city name as parameter and return the highest
	 * temperature of the given city.
	 * 
	 * @param cityName
	 *                  
	 * @return OK 
	 * @return Bad Request
	 * @return Not Found
	 */
	@GetMapping("/high/{cityName}")
	public ResponseEntity<CityTemperatureDto> highestTemperatureByCityName(@PathVariable @Valid String cityName) {

		if (cityName == null) {
			return ResponseEntity.badRequest().build();
		}

		Temperature highest = cityTempService.highest(cityName);

		if (highest != null) {

			CityTemperatureDto auxDto = cityTempService.fillDtoWithTemperature(highest);

			return ResponseEntity.ok(auxDto);

		}

		return ResponseEntity.notFound().build();
	}

	/**
     * This method receive a city name as parameter and return the lowest
	 * temperature of the given city.
	 * 
	 * @param cityName
	 *                  
	 * @return OK
	 * @return Bad Request
	 * @return Not Found
	 */
	@GetMapping("/low/{cityName}")
	public ResponseEntity<CityTemperatureDto> lowestTemperatureByCityName(@PathVariable @Valid String cityName) {

		if (cityName == null) {
			return ResponseEntity.badRequest().build();
		}

		Temperature lowest = cityTempService.lowest(cityName);

		if (lowest != null) {

			CityTemperatureDto auxDto = cityTempService.fillDtoWithTemperature(lowest);

			return ResponseEntity.ok(auxDto);

		}

		return ResponseEntity.notFound().build();
	}

	/**
	 * This method receive a city name as parameter and return all
	 * temperatures by the given city.
	 * 
	 * @param cityName
	 *                  
	 * @return OK
	 * @return Bad Request
	 * @return Not Found
	 */
	@GetMapping("/{cityName}")
	public ResponseEntity<CityTemperatureDto> listByCityName(@PathVariable @Valid String cityName) {

		if (cityName == null) {
			return ResponseEntity.badRequest().build();
		}

		CityTemperatureDto temperatureDto = cityTempService.findByCityName(cityName);

		if (temperatureDto != null) {
			return ResponseEntity.ok(temperatureDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	/**
	 * This method list all stored information in DB.
	 * 
	 * @return List<CityTemperatureDto>
	 * @return OK
	 */
	@GetMapping
	public List<CityTemperatureDto> listAll(){
		return cityTempService.listAll();
		
	}

	/**
	 * This method register a new temperature or update the value if the temperature
	 * already exists in a specific date. This method is filled by the user. 
	 * 
	 * @param temperatureDto
	 *                        
	 * @return OK
	 * @return Bad Request
	 */
	@PostMapping
	public ResponseEntity<CityTemperatureDto> register(@RequestBody @Valid CityTemperatureDto temperatureDto) {

		City city = temperatureDto.converterToExistentCity();

		try {
			city = cityTempService.save(city);
			return ResponseEntity.ok(new CityTemperatureDto(city));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	/**
	 * This method delete a register in DB by a given city name.
	 * 
	 * @param cityName
	 *                
	 * @return No Content
	 * @return Not Found
	 */
	@DeleteMapping("/{cityName}")
	public ResponseEntity<?> delete(@PathVariable @Valid String cityName) {
		CityTemperatureDto optTemperature = cityTempService.findByCityName(cityName);

		if (optTemperature != null) {

			City city = optTemperature.converterToExistentCity();
			cityTempService.delete(city);
			return ResponseEntity.noContent().build();

		}

		return ResponseEntity.notFound().build();
	}

}
