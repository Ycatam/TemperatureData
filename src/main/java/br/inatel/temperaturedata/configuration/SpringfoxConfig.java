package br.inatel.temperaturedata.configuration;

import javax.management.Notification;

import org.aspectj.bridge.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * 
 * @author rafael.rocha
 *
 */


@Configuration
public class SpringfoxConfig {
	
	/**
	 * 
	 * Configuration class for Swagger documentation.
	 * 
	 */

	@Bean
	public Docket QuoteManagerAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.inatel.temperaturedata"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(Message.class, Notification.class)
				.apiInfo(new ApiInfoBuilder()
						.title("Temperature Data")
						.description("Application that manages and store temperatures from historical database or actual, to provide data for Agrotech Industries.")
						.build());
	}

	
}
