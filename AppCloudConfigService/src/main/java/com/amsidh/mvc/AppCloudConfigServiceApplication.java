package com.amsidh.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class AppCloudConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppCloudConfigServiceApplication.class, args);
	}

}
