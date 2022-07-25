package br.inatel.temperaturedata.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * 
 * @author rafael.rocha
 * 
 * This Dto is used to handle the nested Json that cames from the
 * Stormglass API.
 *
 */
public class AirTemperatureDto {

	@JsonAlias("sg")
	private Double stormglass;

	// Constructors...

	public AirTemperatureDto() {
	}

	// Accessors...

	public AirTemperatureDto(Double stormglass) {
		this.stormglass = stormglass;
	}


	public Double getStormglass() {
		return stormglass;
	}

	public void setStormglass(Double stormglass) {
		this.stormglass = stormglass;
	}

}
