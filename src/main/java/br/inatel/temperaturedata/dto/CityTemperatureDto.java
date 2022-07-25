package br.inatel.temperaturedata.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.entities.Temperature;

/**
 * 
 * @author rafael.rocha
 * 
 * This Dto handle all the information to the controller
 * TemperatureDataController.
 *
 */

public class CityTemperatureDto {

	private Long id;

	@NotNull
	@NotBlank
	@NotEmpty
	private String cityName;

	private Double latitude;

	private Double longitude;

	private Map<LocalDate, Double> temperatures = new HashMap<>();

	// Constructors...
	public CityTemperatureDto() {
	}

	public CityTemperatureDto(City city) {

		this.id = city.getId();
		this.cityName = city.getCityName();
		this.latitude = city.getLatitude();
		this.longitude = city.getLongitude();
		city.getTemperatures().forEach(t -> {
			this.temperatures.put(t.getDate(), t.getTemperature());
		});

	}

	// Methods

	/**
	 * This method convert a Dto to a previous registered City Entity.
	 * 
	 * @return City
	 */
	public City converterToExistentCity() {
		City city = new City();
		if (this.id != null) {
			city.setId(id);
		}

		city.setCityName(this.cityName);
		city.setLatitude(this.latitude);
		city.setLongitude(this.longitude);

		this.temperatures.entrySet().stream().map(e -> new Temperature(e.getKey(), e.getValue(), city))
				.forEach(t -> city.addTemperature(t));

		return city;
	}

	/**
	 * This method convert the City entity in the CityTemperatureDto.
	 * 
	 * @param City
	 * 
	 * @return List<CityTemperatureDto>
	 */
	public static List<CityTemperatureDto> convertToDto(List<City> city) {
		return city.stream().map(CityTemperatureDto::new).collect(Collectors.toList());
	}

	// Accessors...

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Map<LocalDate, Double> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(Map<LocalDate, Double> temperatures) {
		this.temperatures = temperatures;
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

}
