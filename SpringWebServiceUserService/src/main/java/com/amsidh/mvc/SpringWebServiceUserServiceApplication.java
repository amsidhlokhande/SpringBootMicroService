package com.amsidh.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.retry.annotation.EnableRetry;

import com.amsidh.mvc.feign.client.album.AlbumsServiceFeignClient;
import com.amsidh.mvc.repository.user.entity.UserRepository;

@EnableEurekaClient
@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = {UserRepository.class})
@RefreshScope
@EnableFeignClients(basePackageClasses = AlbumsServiceFeignClient.class)
@EnableAutoConfiguration
@EnableRetry
public class SpringWebServiceUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServiceUserServiceApplication.class, args);
    }

}
