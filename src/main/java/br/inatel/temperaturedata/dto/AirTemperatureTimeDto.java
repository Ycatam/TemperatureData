package br.inatel.temperaturedata.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author rafael.rocha
 * 
 * This Dto is used to handle the nested Json that came from the
 * Stormglass API.
 *
 */

public class AirTemperatureTimeDto {
	
	private AirTemperatureDto airTemperature;
	
	private LocalDateTime time;
	
	//Accessors...

	public AirTemperatureDto getAirTemperature() {
		return airTemperature;
	}

	public void setAirTemperature(AirTemperatureDto airTemperature) {
		this.airTemperature = airTemperature;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = LocalDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

}
