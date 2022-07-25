package br.inatel.temperaturedata.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.temperaturedata.adapter.RequestTemperatureAdapter;
import br.inatel.temperaturedata.dto.CityTemperatureDto;
import br.inatel.temperaturedata.dto.StormglassDataDto;
import br.inatel.temperaturedata.dto.StormglassRequestParametersDto;
import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.services.CityTemperatureService;

/**
 * 
 * @author rafael.rocha
 *
 */

@RestController()
@RequestMapping("/fromstormglass")
public class StormgalssController {

	private final CityTemperatureService cityTempService;
	private final RequestTemperatureAdapter extAdapter;

	public StormgalssController(CityTemperatureService cityTempService, 
			RequestTemperatureAdapter extAdapter) {
		this.cityTempService = cityTempService;
		this.extAdapter = extAdapter;
	}

	/**
	 * This method use the adapter to do a request in Stormglass API, call
	 * the methods to handle the Json from Stormglass to transform in a object used by the project.
	 * 
	 * @param stormglassDataDto
	 * 
	 * @return OK
	 * @return Bad Request;
	 * 
	 */
	@PostMapping
	public ResponseEntity<CityTemperatureDto> registerFromStormglass(@RequestBody StormglassDataDto stormglassDataDto) {
		StormglassRequestParametersDto opExtDto = extAdapter.requireExtApi(stormglassDataDto);
		CityTemperatureDto cityTempDto = stormglassDataDto.converterFromStormglassToCityTempDto(opExtDto);
		City city = cityTempDto.converterToExistentCity();

		try {
			city = cityTempService.saveFromStormglass(city);
			return ResponseEntity.ok(new CityTemperatureDto(city));

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
}