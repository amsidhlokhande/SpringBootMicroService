package com.amsidh.mvc.service;

import com.amsidh.mvc.model.HealthResponseModel;
import reactor.core.publisher.Mono;

public interface AccountService {

    Mono<HealthResponseModel>  healthStatus();
}
