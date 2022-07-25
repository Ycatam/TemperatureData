package br.inatel.temperaturedata.entities;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 
 * @author rafael.rocha
 *
 *Entity Temperature
 */

@Entity
public class Temperature {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private LocalDate date;
	
	@NotNull
	private Double temperature;
	
	@ManyToOne
	private City city;
	
	//Constructors...
	
	public Temperature() {
	}

	public Temperature(LocalDate date, Double temperature, City city) {
		this.date = date;
		this.temperature = temperature;
		this.city = city;
	}
	
	//Accessors...

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, temperature);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Temperature other = (Temperature) obj;
		return Objects.equals(date, other.date) && Objects.equals(temperature, other.temperature);
	}
	
}
