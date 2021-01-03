package com.amsidh.mvc;

import com.amsidh.mvc.repository.user.entity.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class SpringWebServiceUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServiceUserServiceApplication.class, args);
    }

}
