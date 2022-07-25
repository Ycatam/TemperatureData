package br.inatel.temperaturedata.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.temperaturedata.dto.CityDto;
import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.services.CityTemperatureService;

/**
 * 
 * @author rafael.rocha
 *
 * This controller is responsible for handle the requests to register a
 * city in DB
 */

@RestController()
@RequestMapping("/city")
public class CityRegisterController {
	
	private final CityTemperatureService cityTemperatureService;
	
	public CityRegisterController(CityTemperatureService cityTemperatureService) {
		this.cityTemperatureService = cityTemperatureService;
	}
	
	/**
	 * This method register a city in DB. Receive a Dto as parameter, call
	 * a convert method from Dto do Entity, the stores in DB via service layer.
	 * 
	 *  
	 * @param cityDto
	 * 
	 * @return OK
	 * @return Bad Request
	 */
	
	@PostMapping
	public ResponseEntity<CityDto> registerCity(@RequestBody @Valid CityDto cityDto){
		
		City city = cityDto.converterToCityLatLng();
		
		try {
			city = cityTemperatureService.saveCity(city);
			return ResponseEntity.ok(new CityDto(city));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	/**
	 * This method list all city registered in DB.
	 * 
	 * @return List<CityDto>
	 * @return OK
	 */
	@GetMapping
	public List<CityDto> listAllRegisteredCity(){
		return cityTemperatureService.listAllCity();
	}

}
