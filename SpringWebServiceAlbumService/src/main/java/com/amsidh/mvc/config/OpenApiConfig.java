package com.amsidh.mvc.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI getCustomOpenApi() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
				.info(new Info().title("Album Web Service").license(new License().name("Apache 2.0").url("http://amsidhit.co.in")));
	}
	

	@Bean
	public GroupedOpenApi getAlbumsGroupOpenApi() {
		return GroupedOpenApi.builder().group("albums").pathsToMatch("/albums/**").build();
	}
}
