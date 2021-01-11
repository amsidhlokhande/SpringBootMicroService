package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.model.HealthResponseModel;
import com.amsidh.mvc.service.AccountService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AccountServiceImpl implements AccountService {


    @Override
    public Mono<HealthResponseModel> healthStatus() {
        return Mono.justOrEmpty(new HealthResponseModel("Account Micro service is up and running"));
    }
}
