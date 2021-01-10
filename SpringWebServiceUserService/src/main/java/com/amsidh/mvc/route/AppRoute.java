package com.amsidh.mvc.route;

import com.amsidh.mvc.handler.SignInSignUpHandler;
import com.amsidh.mvc.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class AppRoute {

    private static final String USER_HOME_URL = "/users";
    private static final String USER_HOME_WITH_USERID_URL = "/users/{userId}";
    private static final String USER_HOME_WITH_USERNAME_URL = "/users/{username}";
    private final String PERSON_HOME_URL = "/persons";
    private final String PERSON_HOME_USERID_URL = "/persons/{userId}";
    private final String PERSON_HOME_USERNAME_URL = "/persons/{username}";

    @Bean
    public RouterFunction getUserWsHealthCheck(UserHandler userHandler) {
        log.info("AppRoute getUserWsHealthCheck method called");
        return route(GET(USER_HOME_URL + "/health/check").and(accept(APPLICATION_JSON)), userHandler::healthCheck);
    }

    @Bean
    public RouterFunction getUserRoute(UserHandler userHandler) {
        log.info("AppRoute getUserRoute method called");
        return route(GET(USER_HOME_URL).and(accept(APPLICATION_JSON)), userHandler::getAllUsers).and(
                route(POST(USER_HOME_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::createUser)).and(
                route(PATCH(USER_HOME_WITH_USERID_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::updateUser)).and(
                route(GET(USER_HOME_WITH_USERID_URL).and(accept(APPLICATION_JSON)), userHandler::getUserById)).and(
                route(DELETE(USER_HOME_WITH_USERID_URL), userHandler::deleteUserById)).and(
                route(GET(USER_HOME_WITH_USERNAME_URL).and(accept(APPLICATION_JSON)), userHandler::getUserByUsername));
    }

    @Bean
    public RouterFunction getSignInSignUp(SignInSignUpHandler signInSignUpHandler) {
        log.info("AppRoute getSignInSignUp method called");
        return route(POST(USER_HOME_URL + "/signIn").and(accept(APPLICATION_JSON)), signInSignUpHandler::signIn).and(
                route(POST(USER_HOME_URL + "/signUp").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), signInSignUpHandler::signUp));
    }

    @Bean
    public RouterFunction getPersonsRoute(UserHandler userHandler) {
        log.info("AppRoute getPersonsRoute method called");
        return route(GET(PERSON_HOME_URL).and(accept(APPLICATION_JSON)), userHandler::getAllUsers).and(
                route(POST(PERSON_HOME_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::createUser)).and(
                route(PATCH(PERSON_HOME_USERID_URL).and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), userHandler::updateUser)).and(
                route(GET(PERSON_HOME_USERID_URL).and(accept(APPLICATION_JSON)), userHandler::getUserById)).and(
                route(DELETE(PERSON_HOME_USERID_URL), userHandler::deleteUserById)).and(
                route(GET(PERSON_HOME_USERNAME_URL).and(accept(APPLICATION_JSON)), userHandler::getUserByUsername));
    }
}