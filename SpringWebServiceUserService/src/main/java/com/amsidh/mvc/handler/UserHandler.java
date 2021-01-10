package com.amsidh.mvc.handler;

import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.model.model.UserResponseModel;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> healthCheck(ServerRequest serverRequest) {
        log.info("UserHandler healthCheck method called");
        return ServerResponse.ok().body(Mono.justOrEmpty("USER-WS Service up and running"), String.class);
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        log.info("UserHandler getAllUsers method called");
        return ServerResponse.ok().body(userService.getUsers().flatMap(userDto -> Mono.justOrEmpty(ModelMapperUtil.convertToUserResponseModel(userDto))), UserResponseModel.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        log.info("UserHandler getUserById method called");
        return ServerResponse.ok().body(userService.getUser(serverRequest.pathVariable("userId")).flatMap(userDto -> Mono.justOrEmpty(ModelMapperUtil.convertToUserResponseModel(userDto))), UserResponseModel.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        log.info("UserHandler createUser method called");
        Mono<UserDto> userDtoMono = serverRequest.bodyToMono(UserDto.class).flatMap(userDto -> userService.createUser(userDto));
        return ServerResponse.ok().body(userDtoMono.flatMap(userDto -> Mono.justOrEmpty(ModelMapperUtil.convertToUserResponseModel(userDto))), UserResponseModel.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        log.info("UserHandler updateUser method called");
        Mono<UserDto> userDtoMono = serverRequest.bodyToMono(UserDto.class).flatMap(userDto -> userService.updateUser(serverRequest.pathVariable("userId"), userDto));
        return ServerResponse.ok().body(userDtoMono.flatMap(userDto -> Mono.justOrEmpty(ModelMapperUtil.convertToUserResponseModel(userDto))), UserResponseModel.class);
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
        log.info("UserHandler deleteUserById method called");
        return ServerResponse.ok().body(userService.deleteUser(serverRequest.pathVariable("userId")), Void.class);
    }

    public Mono<ServerResponse> getUserByUsername(ServerRequest serverRequest) {
        log.info("UserHandler getUserByUsername method called");
        return ServerResponse.ok().body(userService.getUserEntityByEmailId(serverRequest.pathVariable("username")), UserDto.class);
    }
}
