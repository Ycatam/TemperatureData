package br.inatel.temperaturedata.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.inatel.temperaturedata.dto.CityDto;
import br.inatel.temperaturedata.dto.CityTemperatureDto;
import br.inatel.temperaturedata.entities.City;
import br.inatel.temperaturedata.entities.Temperature;
import br.inatel.temperaturedata.repositories.CityRepository;
import br.inatel.temperaturedata.repositories.TemperatureRepository;

/**
 * 
 * @author rafael.rocha
 * 
 * Service class, layer between controller and repository.
 */

@Service
@Transactional
public class CityTemperatureService {

	private CityRepository cityRepository;
	private TemperatureRepository temperatureRepository;

	public CityTemperatureService(CityRepository cityRepository, TemperatureRepository temperatureRepository) {
		this.cityRepository = cityRepository;
		this.temperatureRepository = temperatureRepository;
	}

	/**
	 * Method that requires the highest temperature entry in DB via repository
	 * 
	 * @param cityName
	 * 
	 * @return an optional if found
	 * @return null if noting was found
	 */
	public Temperature highest(String cityName) {
		Optional<City> opCity = cityRepository.findByCityName(cityName);

		if (opCity.isPresent()) {

			CityTemperatureDto tempDto = new CityTemperatureDto(opCity.get());

			LocalDate highestEntry = tempDto.getTemperatures().entrySet().stream()
					.max((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
			Optional<Temperature> findTemperatureByDate = temperatureRepository.findTemperatureByDateAndCityId(highestEntry, tempDto.getId());
			return findTemperatureByDate.get();

		}
		return null;
	}

	/**
	 * Method that requires the lowest temperature entry in DB via repository
	 * 
	 * @param cityName
	 * 
	 * @return an optional if found
	 * @return null if noting was found
	 */
	public Temperature lowest(String cityName) {
		Optional<City> opCity = cityRepository.findByCityName(cityName);

		if (opCity.isPresent()) {

			CityTemperatureDto tempDto = new CityTemperatureDto(opCity.get());

			LocalDate highestEntry = tempDto.getTemperatures().entrySet().stream()
					.min((e1, e2) -> e1.getValue() > e2.getValue() ? 1 : -1).get().getKey();
			Optional<Temperature> findTemperatureByDate = temperatureRepository.findTemperatureByDateAndCityId(highestEntry, tempDto.getId());
			return findTemperatureByDate.get();
		}
		return null;
	}
	
	/**
	 * This is a auxiliary method to fill all the data to a Dto object
	 * used by methods that get via repository, the optional of temperature.
	 * 
	 * @param temperature
	 * 
	 * @return CityTemperatureDto
	 */
	public CityTemperatureDto fillDtoWithTemperature(Temperature temperature) {
		
		HashMap<LocalDate, Double> tempMap = new HashMap<LocalDate,Double>();
		
		CityTemperatureDto auxDto = new CityTemperatureDto();
		auxDto.setId(temperature.getCity().getId());
		auxDto.setCityName(temperature.getCity().getCityName());
		auxDto.setLatitude(temperature.getCity().getLatitude());
		auxDto.setLongitude(temperature.getCity().getLongitude());
		tempMap.put(temperature.getDate(), temperature.getTemperature());
		auxDto.setTemperatures(tempMap);
		
		return auxDto;
	}

	/**
	 * This method find in DB a City by city name
	 * @param cityName
	 * 
	 * @return CityTemperatureDto
	 * @return null if nothing was found
	 */
	public CityTemperatureDto findByCityName(String cityName) {
		Optional<City> opCity = cityRepository.findByCityName(cityName);

		if (opCity.isPresent()) {

			CityTemperatureDto tempDto = new CityTemperatureDto(opCity.get());
			return tempDto;
		}
		return null;
	}
	
	/**
	 * This method list all the temperature by city, from all city stored in DB.
	 * 
	 * @return List<CityTemperatureDto>
	 */
	public List<CityTemperatureDto> listAll() {
		
		List<City> listAll = cityRepository.findAll();
		return CityTemperatureDto.convertToDto(listAll);
	}
	
	/**
	 * This method list all registered city in DB.
	 * 
	 * @return List<CityDto>
	 */
	public List<CityDto> listAllCity() {
		
		List<City> listAllCity = cityRepository.findAll();
		return CityDto.convertToDto(listAllCity);
	}

	/**
	 * This method is responsible to store in DB the temperature of a registered City.
	 * 
	 * @param city
	 * 
	 * @return City
	 * 
	 * @throws Exception with the message city isn't registered
	 */
	public City save(City city) throws Exception {
		List<Temperature> listTemperature = city.getTemperatures();

		verifyIfCityIsRegistered(city);

		saveCity(city);

		if (!listTemperature.isEmpty()) {
			listTemperature.forEach(t -> saveListTemperature(t));
		}
		return city;

	}

	/**
	 * Auxiliary method to the save method. This method is responsible to search in DB and verify
	 * if the city already exists by a given city name, and update its value.
	 *
	 * @param city
	 * 
	 * @return The found city or the city saved.
	 */
	public City saveCity(City city) {

		Optional<City> opCity = cityRepository.findByCityName(city.getCityName());
		if (opCity.isPresent()) {

			City cityAux = opCity.get();
			city.setId(cityAux.getId());
			city.setLatitude(cityAux.getLatitude());
			city.setLongitude(cityAux.getLongitude());

		}

		city = cityRepository.save(city);
		return city;

	}

	/**
	 * Auxiliary method to the save method. This method verify if by a given date and city Id already exists a
	 * temperature stored in DB. Updating previous values stored or storing a new value.
	 * 
	 * @param temperature
	 * 
	 * @return The found object
	 * @return The new stored object
	 */
	private Temperature saveListTemperature(Temperature temperature) {

		Optional<Temperature> opTemperature = temperatureRepository.findByDateAndCityId(temperature.getDate(),
				temperature.getCity().getId());

		if (opTemperature.isPresent()) {

			Temperature existentTemp = opTemperature.get();
			existentTemp.setTemperature(temperature.getTemperature());
			return existentTemp;
		} else {

			temperature = temperatureRepository.save(temperature);
			return temperature;

		}

	}
	
	/**
	 * This method save the data from the Stormglass API in the DB only if the city is already registered.
	 * 
	 * @param city
	 * 
	 * @return City if saved
	 * @return Null
	 */
	public City saveFromStormglass(City city){
		List<Temperature> listTemperature = city.getTemperatures();
		
		Optional<City> opCity = cityRepository.findByLatitudeAndLongitude(city.getLatitude(), city.getLongitude());
		
		if (opCity.isPresent()) {
			
			City cityAux = opCity.get();
			city.setId(cityAux.getId());
			city.setCityName(cityAux.getCityName());
			city.setLatitude(cityAux.getLatitude());
			city.setLongitude(cityAux.getLongitude());
			city.setTemperatures(listTemperature);
			
			saveCity(city);
			listTemperature.forEach(t -> saveListTemperature(t));
			
			return city;
			
		}
		
		return null;
	}

	/**
	 * This is a auxiliary method that checks if the city that we want to store its temperature
	 * is already registered.
	 * 
	 * @param city
	 * 
	 * @return The city object found if it already exists.
	 * 
	 * @throws Exception If the city don't exist
	 */
	private CityTemperatureDto verifyIfCityIsRegistered(City city) throws Exception {

		CityTemperatureDto cityAux = findByCityName(city.getCityName());

		if (cityAux.getCityName().isEmpty()) {

			throw new Exception("City not registered, register it first!");

		}

		return cityAux;

	}

	/**
	 * This method delete a register in DB by a valid city name via repository.
	 * 
	 * @param city
	 */
	public void delete(City city) {
		List<Temperature> opTemp = temperatureRepository.findAllByCityId(city.getId());

		for (Temperature temperature : opTemp) {
			temperatureRepository.delete(temperature);
		}

		cityRepository.delete(city);

	}

}
