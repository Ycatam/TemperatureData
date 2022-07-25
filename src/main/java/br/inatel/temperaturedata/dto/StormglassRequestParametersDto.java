package br.inatel.temperaturedata.dto;

import java.util.List;

/**
 * 
 * @author rafael.rocha
 *
 * This Dto handle all the data from the Stormglass API.
 */
public class StormglassRequestParametersDto {

	private List<AirTemperatureTimeDto> hours;

	private StormglassMetaDto meta;

	// Constructors

	public StormglassRequestParametersDto() {
	}

	public StormglassRequestParametersDto(List<AirTemperatureTimeDto> hours) {
		this.hours = hours;
	}

	// Accessors

	public List<AirTemperatureTimeDto> getHours() {
		return hours;
	}

	public void setHours(List<AirTemperatureTimeDto> hours) {
		this.hours = hours;
	}

	public StormglassMetaDto getMeta() {
		return meta;
	}

	public void setMeta(StormglassMetaDto meta) {
		this.meta = meta;
	}

}
