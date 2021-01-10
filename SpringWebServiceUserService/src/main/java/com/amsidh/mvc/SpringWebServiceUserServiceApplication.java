package com.amsidh.mvc;

import com.amsidh.mvc.repository.user.entity.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories(basePackageClasses = {UserRepository.class})
@RefreshScope
public class SpringWebServiceUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServiceUserServiceApplication.class, args);
    }

}
