package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket getDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getApiInfo() {

		return new ApiInfoBuilder()
				.title("Account Management Web Service")
				.description("Account Management Web Service").termsOfServiceUrl("http://www.amsidhlokhande.com/")
				.contact(new Contact("Amsidh Lokhande", "http://www.amsidhlokhande.com/", "amsidhlokhande@gmail.com"))
				.version("1.0").build();
	}
}
