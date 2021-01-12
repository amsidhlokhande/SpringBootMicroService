package com.amsidh.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.amsidh.mvc.repository.AlbumRepository;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = { AlbumRepository.class })
@RefreshScope
public class SpringWebServiceAlbumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebServiceAlbumServiceApplication.class, args);
	}

}
