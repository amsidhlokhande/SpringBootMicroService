package com.amsidh.mvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AccountHandler {


    public AccountHandler(){
        log.info("Loading AccountHolder!!!!");
    }

    public Mono<ServerResponse> healthCheck(ServerRequest serverRequest){
        log.info("AccountHandler healthCheck method called");
        return ServerResponse.ok().body(Mono.justOrEmpty(new HealthResponse("Account-ws service is up and running.")), HealthResponse.class);
    }
}

@Data
@AllArgsConstructor
class HealthResponse{
    private String appStatus;
}
