package com.amsidh.mvc.routes;

import com.amsidh.mvc.handler.AccountHandlerFunction;
import com.amsidh.mvc.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@Configuration
@Slf4j
public class AccountRouterFunction {

    public static final String ACCOUNTS_BASE_URL = "/accounts";
    public static final String ACCOUNTS_BASE_WITH_ACCOUNT_ID_URL = "/accounts/{accountId}";
    public static final String ACCOUNTS_HEALTH_CHECK = ACCOUNTS_BASE_URL + "/health/check";

    @Bean
    public RouterFunction<ServerResponse> getAccountRoutes(AccountHandlerFunction accountHandlerFunction) {
        log.info("AccountRouterFunction getAccountRoutes method called");
        return route().GET(ACCOUNTS_HEALTH_CHECK, accept(APPLICATION_JSON), accountHandlerFunction.healthCheck(), ops -> ops.beanClass(AccountService.class).beanMethod("healthStatus")).build()
                .and(route().GET("ACCOUNTS_BASE_URL", accept(APPLICATION_JSON), accountHandlerFunction.getAllAccounts(), builder -> builder.beanClass(AccountService.class).beanMethod("getAllAccounts")).build())
                .and(route().GET("ACCOUNTS_BASE_WITH_ACCOUNT_ID_URL", accept(APPLICATION_JSON), accountHandlerFunction.getAccountById(), builder -> builder.beanClass(AccountService.class).beanMethod("getAccountByAccountId")).build())
                .and(route().POST(ACCOUNTS_BASE_URL, accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON)), accountHandlerFunction.createAccount(), builder -> builder.beanClass(AccountService.class).beanMethod("createAccount")).build())
                .and(route().PATCH(ACCOUNTS_BASE_WITH_ACCOUNT_ID_URL, accept(APPLICATION_JSON).and(contentType(APPLICATION_JSON)), accountHandlerFunction.updateAccount(), builder -> builder.beanClass(AccountService.class).beanMethod("updateAccount")).build())
                .and(route().DELETE(ACCOUNTS_BASE_WITH_ACCOUNT_ID_URL, accountHandlerFunction.deleteAccount(), builder -> builder.beanClass(AccountService.class).beanMethod("deleteAccountByAccountId")).build());
    }
}
