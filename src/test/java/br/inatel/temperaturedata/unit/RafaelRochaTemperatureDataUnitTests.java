package br.inatel.temperaturedata.unit;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.inatel.temperaturedata.dto.CityDto;
import br.inatel.temperaturedata.dto.CityTemperatureDto;
import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.entities.Temperature;
import br.inatel.temperaturedata.services.CityTemperatureService;

@ActiveProfiles("test")
@SpringBootTest
public class RafaelRochaTemperatureDataUnitTests {

	@Autowired
	private CityTemperatureService cts;
	
	private final String cityName = "sao paulo";
	private final String invalidCityName = "pipoca";
	private final Double lat = 35.8;
	private final Double lng = -10.2;
	private final Double high = 30.0;
	private final Double low = 6.0;
	
	@Test
	void givenValidCityName_whenHighestTempIsRequired_thenShouldReturnOptTemperature() {
		
		Temperature highOpt = cts.highest(cityName);
		
		Double highCts = highOpt.getTemperature();
		
		Assert.assertEquals(highCts, high);
		
	}
	
	@Test
	void givenInvalidCityName_whenHighestTempIsRequired_thenShouldReturnNull() {
		
		Temperature highOpt = cts.highest(invalidCityName);
		
		Assert.assertEquals(highOpt, null);
		
	}
	
	@Test
	void givenValidCityName_whenLowestTempIsRequired_thenShouldReturnOptTemperature() {
		
		Temperature lowOpt = cts.lowest(cityName);
		
		Double lowCts = lowOpt.getTemperature();
		
		Assert.assertEquals(lowCts, low);
		
	}
	
	@Test
	void givenInvalidCityName_whenLowestTempIsRequired_thenShouldReturnNull() {
		
		Temperature lowOpt = cts.lowest(invalidCityName);
		
		Assert.assertEquals(lowOpt, null);
		
	}
	
	@Test
	void givenValidCityName_whenFindByCityNameIsRequired_thenShouldReturnOptTemperature() {

		CityTemperatureDto findOpt = cts.findByCityName(cityName);
		Double latitude = findOpt.getLatitude();
		Double longitude = findOpt.getLongitude();
		
		Assert.assertEquals(cityName, findOpt.getCityName());
		Assert.assertEquals(latitude, lat);
		Assert.assertEquals(longitude, lng);
		
	}
	
	@Test
	void givenInvalidCityName_whenFindByCityNameIsRequired_thenShouldReturnNull() {

		CityTemperatureDto findOpt = cts.findByCityName(invalidCityName);
		
		Assert.assertEquals(findOpt, null);
	}
	
	@Test
	void givenListAllStoredInfo_whenGetAllIsRequired_thenShouldReturnListDto() {
	
		List<CityTemperatureDto> listAll = cts.listAll();
		
		Assert.assertEquals(listAll.get(0).getCityName(), cityName);
		
	}
	
	@Test
	void givenAllRegisteredCity_whenListAllRegistredCityIsRequired_thenShouldReturnListCityDto() {
		
		List<CityDto> listAllCity = cts.listAllCity();
		
		Assert.assertEquals(listAllCity.get(0).getCityName(), cityName);
		
	}
	
	@Test
	void givenValidCity_whenSaveCityIsRequired_thenShouldReturnCity() throws Exception {
		Temperature temp = new Temperature();
		temp.setDate(LocalDate.of(2022, Month.APRIL, 8));
		temp.setTemperature(20.1);
		
		List<Temperature> listTemp = new ArrayList<>();
		listTemp.add(temp);
		
		City city = new City();
		city.setCityName(cityName);
		city.setLatitude(lat);
		city.setLongitude(lng);
		city.setTemperatures(listTemp);
		temp.setCity(city);
		City city2 = cts.save(city);
		
		Assert.assertEquals(city, city2);
		
	}
	
	@Test
	void givenValidCity_whenSaveFromStormglass_thenShouldReturnCity() {
		
		Temperature temp = new Temperature();
		temp.setDate(LocalDate.of(2022, Month.APRIL, 8));
		temp.setTemperature(20.1);
		
		List<Temperature> listTemp = new ArrayList<>();
		listTemp.add(temp);
		
		City city = new City();
		city.setCityName(cityName);
		city.setLatitude(lat);
		city.setLongitude(lng);
		city.setTemperatures(listTemp);
		temp.setCity(city);

		City city2 = cts.saveFromStormglass(city);
		
		Assert.assertEquals(city, city2);
	}
	
}
