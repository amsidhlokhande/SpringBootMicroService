package com.amsidh.mvc.route;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handler.signin.SignInHandler;
import com.amsidh.mvc.handler.user.UserHandler;
import com.amsidh.mvc.handlers.signup.SignUpHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AppRoute {

    private static final String USER_HOME_URL = "/users";
    private static final String USER_HOME_WITH_USERID_URL = "/users/{userId}";
    private final String PERSON_HOME_URL = "/persons";
    private final String PERSON_HOME_USERID_URL = "/persons/{userId}";


    @Bean
    public RouterFunction<ServerResponse> getUserRoute(UserHandler userHandler) {
        log.debug("AppRoute getUserRoute method called");
        return route(GET(USER_HOME_URL).and(accept(APPLICATION_JSON)), userHandler::getAllUsers).and(
                route(PATCH(USER_HOME_WITH_USERID_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::updateUser)).and(
                route(GET(USER_HOME_WITH_USERID_URL).and(accept(APPLICATION_JSON)), userHandler::getUserById)).and(
                route(DELETE(USER_HOME_WITH_USERID_URL), userHandler::deleteUserById));
    }

    @Bean
    public RouterFunction<ServerResponse>  getSignInRoutes(SignInHandler signInHandler) {
        log.debug("AppRoute getSignInSignUp method called");
        return route(POST(USER_HOME_URL + "/signIn").and(accept(APPLICATION_JSON)), signInHandler::signIn);
    }
    
    @Bean
    public RouterFunction<ServerResponse>  getSignUpRouter(SignUpHandler signUpHandler) {
        log.debug("AppRoute getSignInSignUp method called");
        return route(POST(USER_HOME_URL + "/signUp").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), signUpHandler::signUp);
    }
    

    @Bean
    public RouterFunction<ServerResponse>  getPersonsRoute(UserHandler userHandler) {
        log.debug("AppRoute getPersonsRoute method called");
        return route(GET(PERSON_HOME_URL).and(accept(APPLICATION_JSON)), userHandler::getAllUsers).and(
                route(PATCH(PERSON_HOME_USERID_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::updateUser)).and(
                route(GET(PERSON_HOME_USERID_URL).and(accept(APPLICATION_JSON)), userHandler::getUserById)).and(
                route(DELETE(PERSON_HOME_USERID_URL), userHandler::deleteUserById));
    }
}