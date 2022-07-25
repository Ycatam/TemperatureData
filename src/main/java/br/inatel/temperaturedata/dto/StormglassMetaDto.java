package br.inatel.temperaturedata.dto;

/**
 * 
 * @author rafael.rocha 
 * This Dto handle the meta information from the Stormglass
 * API where contains the data about latitude and longitude, used in
 * internal classes to store data in DB.
 */
public class StormglassMetaDto {

	private Integer cost;

	private Integer dailyQuota;

	private Double lat;

	private Double lng;

	// Constructors...

	public StormglassMetaDto() {
	}

	public StormglassMetaDto(Integer cost, Integer dailyQuota, Double lat, Double lng) {
		this.cost = cost;
		this.dailyQuota = dailyQuota;
		this.lat = lat;
		this.lng = lng;
	}

	// Accessors...

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getDailyQuota() {
		return dailyQuota;
	}

	public void setDailyQuota(Integer dailyQuota) {
		this.dailyQuota = dailyQuota;
	}

}
