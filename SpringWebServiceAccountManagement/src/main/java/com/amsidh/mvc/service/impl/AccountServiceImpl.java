package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.document.Account;
import com.amsidh.mvc.model.HealthResponseModel;
import com.amsidh.mvc.repository.AccountRepository;
import com.amsidh.mvc.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Mono<HealthResponseModel> healthStatus() {
        return Mono.justOrEmpty(new HealthResponseModel("Account Micro service is up and running"));
    }

    @Override
    public Mono<Account> createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> getAccountByAccountId(String accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public Mono<Account> updateAccount(String accountId, Account account) {
        return getAccountByAccountId(accountId).flatMap(ac -> {
            if (ac.getAccountId().equals(account.getAccountId())) {
                Optional.ofNullable(account.getName()).ifPresent(ac::setName);
                return accountRepository.save(ac);
            } else {
                return Mono.empty();
            }
        });
    }

    @Override
    public Mono<Void> deleteAccountByAccountId(String accountId) {
        return accountRepository.deleteById(accountId);
    }

    @Override
    public Flux<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
