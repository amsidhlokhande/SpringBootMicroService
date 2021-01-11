package com.amsidh.mvc.service;

import com.amsidh.mvc.document.Account;
import com.amsidh.mvc.model.HealthResponseModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<HealthResponseModel> healthStatus();

    Mono<Account> createAccount(Account account);

    Mono<Account> getAccountByAccountId(@Parameter(in = ParameterIn.PATH) String accountId);

    Mono<Account> updateAccount(@Parameter(in = ParameterIn.PATH) String accountId, Account account);

    Mono<Void> deleteAccountByAccountId(@Parameter(in = ParameterIn.PATH) String accountId);

    @Operation(description = "get all the accounts")
    Flux<Account> getAllAccounts();
}
