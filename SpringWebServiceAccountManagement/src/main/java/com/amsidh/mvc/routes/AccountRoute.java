package com.amsidh.mvc.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handler.AccountHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AccountRoute {

    @Bean
    public RouterFunction<ServerResponse> getAccountRoutes(AccountHandler accountHandler){
        log.info("AccountRoute getAccountRoutes method called");
        return RouterFunctions.route(RequestPredicates.GET("/accounts/health/check"), accountHandler::healthCheck);
    }
}
