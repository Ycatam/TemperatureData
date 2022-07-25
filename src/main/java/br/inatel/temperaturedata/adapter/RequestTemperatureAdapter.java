package br.inatel.temperaturedata.adapter;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import br.inatel.temperaturedata.dto.StormglassDataDto;
import br.inatel.temperaturedata.dto.StormglassRequestParametersDto;

/**
 * 
 * @author rafael.rocha
 * 
 * This service class consumes data from the Stormglass API
 * Some of the parameters requests need to be filled in the
 * application.properties file.
 *
 */

@Service
public class RequestTemperatureAdapter {
	
	@Value("${stormglass.url}")
	private String URL_STORMGLASS;
	
	@Value("${stormglass.token}")
	private String AUT_KEY_STORMGLASS;
	
	/**
	 * This method builds the URL with the required parameters to the Stormglass API
	 * And receive in return a Mono based in StormglassRequestParametersDto class.
	 * 
	 * @param stormglassDataDto
	 * @return StormglassDto
	 */
	
	
	public StormglassRequestParametersDto requireExtApi(StormglassDataDto stormglassDataDto){
		
		Double latitude = stormglassDataDto.getLatitude();
		Double longitude = stormglassDataDto.getLongitude();
		LocalDate date = stormglassDataDto.getDate();
		
		try {
			StormglassRequestParametersDto monoCityTemperature = WebClient.create(URL_STORMGLASS)
					.get()
					.uri(uriBuilder -> uriBuilder
							.path("/v2/weather/point")
							.queryParam("lat", "{latitude}")
							.queryParam("lng", "{longitude}")
							.queryParam("params", "airTemperature")
							.queryParam("start", "{date}")
							.build(latitude, longitude, date))
					.header("Authorization", AUT_KEY_STORMGLASS)
					.retrieve()
					.bodyToMono(StormglassRequestParametersDto.class)
					.block();
			
			return monoCityTemperature;
		} catch (WebClientException e) {
			 e.printStackTrace();
		}
		return null;
		
	}
	
}
