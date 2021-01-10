package com.amsidh.mvc.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/accounts")
@Slf4j
public class AccountController {

    public AccountController(){
        log.info("Loading AccountController!!!!");
    }

    @GetMapping(value = "/health/chk")
    public ResponseEntity<HealthResponse> health(){
        return ResponseEntity.ok().body(new HealthResponse("Application is up and running"));
    }

}
