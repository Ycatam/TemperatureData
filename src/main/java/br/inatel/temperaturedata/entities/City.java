package br.inatel.temperaturedata.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author rafael.rocha
 *
 *Entity City
 */

@Entity
public class City {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotBlank
	@NotEmpty
	@Column(nullable = false)
	private String cityName;
	
	@NotNull
	private Double latitude;
	
	@NotNull
	private Double longitude;
	
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private List<Temperature> temperatures = new ArrayList<>();
	
	//Constructors...
	
	public City() {
	}

	public City(String cityName, List<Temperature> temperatures) {
		this.cityName = cityName;
		this.temperatures = temperatures;
	}
	
	public City(String cityName, Double latitude, Double longitude) {
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	//Methods
	
	public void addTemperature(Temperature temperature) {
		this.temperatures.add(temperature);
	}

	//Accessors...

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<Temperature> getTemperatures() {
		return temperatures;
	}

	public void setTemperatures(List<Temperature> temperatures) {
		this.temperatures = temperatures;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		return Objects.hash(cityName, latitude, longitude, temperatures);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		return Objects.equals(cityName, other.cityName) && Objects.equals(latitude, other.latitude)
				&& Objects.equals(longitude, other.longitude) && Objects.equals(temperatures, other.temperatures);
	}

}
