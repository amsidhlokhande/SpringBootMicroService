package com.amsidh.mvc.handler;

import com.amsidh.mvc.document.Account;
import com.amsidh.mvc.model.HealthResponseModel;
import com.amsidh.mvc.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class AccountHandlerFunction {

    private final AccountService accountService;


    public HandlerFunction<ServerResponse> healthCheck() {
        log.info("AccountHandlerFunction healthCheck method called");
        return request -> ServerResponse.ok().body(accountService.healthStatus(), HealthResponseModel.class);
    }

    public HandlerFunction<ServerResponse> createAccount() {
        log.info("AccountHandlerFunction createAccount method called");
        return request -> {
            Mono<Account> accountMono = request.bodyToMono(Account.class);
            Mono<Account> savedAccount = accountMono.flatMap(account -> accountService.createAccount(account));
            return ServerResponse.ok().body(savedAccount, Account.class);
        };
    }

    public HandlerFunction<ServerResponse> getAccountById() {
        log.info("AccountHandlerFunction getAccountById method called");
        return request -> ServerResponse.ok().body(accountService.getAccountByAccountId(request.pathVariable("accountId")), Account.class);
    }

    public HandlerFunction<ServerResponse> updateAccount() {
        log.info("AccountHandlerFunction updateAccount method called");
        return request -> {
            Mono<Account> accountMono = request.bodyToMono(Account.class);
            Mono<Account> updatedAccount = accountMono.flatMap(account -> accountService.updateAccount(request.pathVariable("accountId"), account));
            return ServerResponse.ok().body(updatedAccount, Account.class);
        };
    }

    public HandlerFunction<ServerResponse> deleteAccount() {
        log.info("AccountHandlerFunction deleteAccount method called");
        return request -> ServerResponse.ok().body(accountService.deleteAccountByAccountId(request.pathVariable("accountId")), Void.class);
    }

    public HandlerFunction<ServerResponse> getAllAccounts() {
        log.info("AccountHandlerFunction getAllAccounts method called");
        return request -> ServerResponse.ok().body(accountService.getAllAccounts(), Account.class);
    }

}

