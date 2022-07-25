package br.inatel.temperaturedata.functional;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import br.inatel.temperaturedata.dto.CityDto;
import br.inatel.temperaturedata.dto.CityTemperatureDto;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RafaelRochaTemperatureDataFunctionalTests {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void givenValidCityName_whenHighestTemperatureIsRequired_thenIfValidReturnIsOk() {
	
		String cityName = "sao paulo";
		
		CityTemperatureDto dtoRetrieve = webTestClient.get()
		.uri("/temperatures/high/" + cityName)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(CityTemperatureDto.class)
		.returnResult()
		.getResponseBody()
		;
		
		Assert.assertEquals(dtoRetrieve.getCityName(), cityName);
		
	}
	
	@Test
	void givenInvalidCityName_whenHighestTemperatureIsRequired_thenReturnNotFound() {
	
		String cityName = "pipoca";
		
		webTestClient.get()
		.uri("/temperatures/high/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
		
	}
	
	@Test
	void givenNullCityName_whenHighestTemperatureIsRequired_thenReturnNotFound() {
	
		String cityName = null;
		
		webTestClient.get()
		.uri("/temperatures/high/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
		
	}
	
	@Test
	void givenCityName_whenLowestTemperatureIsRequired_thenIfValidReturnIsOk() {
	
		String cityName = "sao paulo";
		
		CityTemperatureDto dtoRetrieve = webTestClient.get()
		.uri("/temperatures/low/" + cityName)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(CityTemperatureDto.class)
		.returnResult()
		.getResponseBody()
		;
		
		Assert.assertEquals(dtoRetrieve.getCityName(), cityName);
	}
	
	@Test
	void givenInvalidCityName_whenLowestTemperatureIsRequired_thenReturnNotFound() {
	
		String cityName = "pipoca";
		
		webTestClient.get()
		.uri("/temperatures/low/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
		
	}
	
	@Test
	void givenNullCityName_whenLowestTemperatureIsRequired_thenReturnNotFound() {
	
		String cityName = null;
		
		webTestClient.get()
		.uri("/temperatures/low/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound();
		
	}
	
	@Test
	void givenRegisteredCityName_whenListAllTemperatureByCityName_thenIfValidReturnIsOk() {
	
		String cityName = "sao paulo";
		
		CityTemperatureDto dtoRetrieve = webTestClient.get()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isOk()
		.expectBody(CityTemperatureDto.class)
		.returnResult()
		.getResponseBody()
		;
		
		Assert.assertEquals(dtoRetrieve.getCityName(), cityName);
	}
	
	@Test
	void givenNotRegisteredCityName_whenListAllTemperatureByCityName_thenReturnNotFound() {
	
		String cityName = "pipoca";
		
		webTestClient.get()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
	}
	
	@Test
	void givenNullCityName_whenListAllTemperatureByCityName_thenReturnNotFound() {
	
		String cityName = null;
		
		webTestClient.get()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
	}
	
	@Test
	void givenAllRegisteredCity_whenListAllRegisterIsRequired_thenReturnDtoAndOk() {
		
		webTestClient.get()
		.uri("/temperatures")
		.exchange()
		.expectStatus()
		.isOk()
		;
		
	}
	
	@Test
	void givenRegisteredCityName_whenDeleteRegisterByCityName_thenReturnNoContent() {
	
		String cityName = "campinas";
		
		webTestClient.delete()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isNoContent()
		;
	}
	
	@Test
	void givenNotRegisteredCityName_whenDeleteRegisterByCityName_thenReturnNotFound() {
	
		String cityName = "pipoca";
		
		webTestClient.delete()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
	}
	
	@Test
	void givenNullCityName_whenDeleteRegisterByCityName_thenReturnNotFound() {
	
		String cityName = null;
		
		webTestClient.delete()
		.uri("/temperatures/" + cityName)
		.exchange()
		.expectStatus()
		.isNotFound()
		;
	}
	
	@Test
	void givenAValidCityAndTemperature_whenRequestToStoreInDB_thenReturnIsOk() {
		
		CityTemperatureDto cityDto = new CityTemperatureDto();
		Map<LocalDate, Double> temperatureMap = new HashMap<>();
		temperatureMap.put(LocalDate.of(2019, Month.FEBRUARY, 9), 22.0);
		cityDto.setTemperatures(temperatureMap);
		cityDto.setCityName("sao paulo");
		
		webTestClient.post()
		.uri("/temperatures")
		.body(BodyInserters.fromValue(cityDto))
		.exchange()
		.expectStatus()
		.isOk()
		;
		
	}
	
	@Test
	void givenANullCityAndValidTemperature_whenRequestToStoreInDB_thenBadRequest() {
		
		CityTemperatureDto cityTempDto = new CityTemperatureDto();
		Map<LocalDate, Double> temperatureMap = new HashMap<>();
		temperatureMap.put(LocalDate.of(2019, Month.FEBRUARY, 9), 22.0);
		cityTempDto.setTemperatures(temperatureMap);
		cityTempDto.setCityName("");
		
		webTestClient.post()
		.uri("/temperatures")
		.body(BodyInserters.fromValue(cityTempDto))
		.exchange()
		.expectStatus()
		.isBadRequest()
		;
		
	}
	
	@Test
	void givenValidCity_whenRegisterCityIsRequeired_thenReturnisOk() {
		
		CityDto cityDto = new CityDto();
		cityDto.setCityName("sao paulo");
		cityDto.setLatitude(8.0);
		cityDto.setLongitude(-14.3);
		
		webTestClient.post()
		.uri("/city")
		.body(BodyInserters.fromValue(cityDto))
		.exchange()
		.expectStatus()
		.isOk()
		;

	}
	
	@Test
	void givenNullCity_whenRegisterCityIsRequeired_thenReturnBadRequest() {
		
		CityDto cityDto = new CityDto();
		cityDto.setCityName(null);
		cityDto.setLatitude(8.0);
		cityDto.setLongitude(-14.3);
		
		webTestClient.post()
		.uri("/city")
		.body(BodyInserters.fromValue(cityDto))
		.exchange()
		.expectStatus()
		.isBadRequest()
		;

	}
	
	@Test
	void givenEmptyCityName_whenRegisterCityIsRequeired_thenReturnBadRequest() {
		
		CityDto cityDto = new CityDto();
		cityDto.setCityName("");
		cityDto.setLatitude(8.0);
		cityDto.setLongitude(-14.3);
		
		webTestClient.post()
		.uri("/city")
		.body(BodyInserters.fromValue(cityDto))
		.exchange()
		.expectStatus()
		.isBadRequest()
		;
	}
	
	@Test
	void givenAllRegisteredCity_whenListAllRegisteredCityIsRequired_thenReturnDtoAndOk() {
		
		webTestClient.get()
		.uri("/city")
		.exchange()
		.expectStatus()
		.isOk()
		;
		
	}
	
}
