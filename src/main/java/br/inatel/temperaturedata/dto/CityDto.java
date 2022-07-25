package br.inatel.temperaturedata.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.temperaturedata.entities.City;

/**
 * 
 * @author rafael.rocha
 * 
 * This Dto handle the information for a new city register entry.
 *
 */

public class CityDto {

	private Long id;

	@NotNull
	@NotBlank
	@NotEmpty
	private String cityName;

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	// Constructors...

	public CityDto() {
	}

	public CityDto(City city) {
		this.id = city.getId();
		this.cityName = city.getCityName();
		this.latitude = city.getLatitude();
		this.longitude = city.getLongitude();
	}

	// Methods...

	/**
	 * This method convert to a entity City from a cityDto to store a valid city in
	 * DB.
	 * 
	 * @return City
	 */
	public City converterToCityLatLng() {

		City city = new City();
		if (this.id != null) {
			city.setId(id);
		}

		city.setCityName(this.cityName);
		city.setLatitude(this.latitude);
		city.setLongitude(this.longitude);

		return city;
	}
	
	/**
	 * This method convert the City entity in the cityDto.
	 * 
	 * @param city
	 * 
	 * @return CityDto
	 */
	public static List<CityDto> convertToDto(List<City> city) {
		return city.stream().map(CityDto::new).collect(Collectors.toList());
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
