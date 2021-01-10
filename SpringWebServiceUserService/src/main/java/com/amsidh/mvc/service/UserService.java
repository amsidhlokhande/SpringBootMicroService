package com.amsidh.mvc.service;

import com.amsidh.mvc.model.model.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {

    Mono<UserDto> createUser(UserDto user);

    Mono<UserDto> getUser(String userId);

    Mono<UserDto> updateUser(String userId, UserDto user);

    Mono<Void> deleteUser(String userId);

    Flux<UserDto> getUsers();

    Mono<UserDto> getUserEntityByEmailId(String userName);
}
