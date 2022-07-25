package br.inatel.temperaturedata.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author rafael.rocha
 * 
 * This Dto handle the data to the StormglassController
 *
 */

public class StormglassDataDto {

	@NotNull
	@NotEmpty
	@NotBlank
	private String cityName;

	@NotNull
	@NotEmpty
	@NotBlank
	private Double latitude;

	@NotNull
	@NotEmpty
	@NotBlank
	private Double longitude;

	@NotNull
	@NotEmpty
	@NotBlank
	private LocalDate date;
	
	// Constructors...

	public StormglassDataDto() {
	}

	public StormglassDataDto(Double latitude, Double longitude, LocalDate date) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.date = date;
	}

	// Methods...

	/**
	 * This method receive all the data from StormglassRequestParameterDto and convert to
	 * the CityTemperatureDto, which is capable to convert to a entity. This method also store
	 * the temperature in the 12h time stamp. It's arbitrary, in this case the author choose the hour 12
	 * to acquire the temperature in that day.
	 * 
	 * @param stormglassDto
	 *                      
	 * @return a CityTemperatureDto
	 */
	public CityTemperatureDto converterFromStormglassToCityTempDto(StormglassRequestParametersDto stormglassDto) {

		CityTemperatureDto cityDto = new CityTemperatureDto();
		Map<LocalDate, Double> temperatures = new HashMap<>();

		List<AirTemperatureTimeDto> listTemp = stormglassDto.getHours();
		StormglassMetaDto meta = stormglassDto.getMeta();

		for (AirTemperatureTimeDto auxDto : listTemp) {

			int hour = auxDto.getTime().getHour();

			if (hour == 12) {

				Double tempDto = auxDto.getAirTemperature().getStormglass();
				LocalDate dateDto = auxDto.getTime().toLocalDate();
				temperatures.put(dateDto, tempDto);
				cityDto.setTemperatures(temperatures);

			}

		}

			cityDto.setLatitude(meta.getLat());
			cityDto.setLongitude(meta.getLng());

		return cityDto;
	}

	// Accessors...

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}