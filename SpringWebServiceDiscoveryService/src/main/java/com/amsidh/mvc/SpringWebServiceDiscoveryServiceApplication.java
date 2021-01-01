package com.amsidh.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringWebServiceDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebServiceDiscoveryServiceApplication.class, args);
	}

}
