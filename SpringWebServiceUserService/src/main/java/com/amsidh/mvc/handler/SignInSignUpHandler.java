package com.amsidh.mvc.handler;

import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.model.model.UserResponseModel;
import com.amsidh.mvc.security.JwtUtil;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class SignInSignUpHandler {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public Mono<ServerResponse> signIn(ServerRequest serverRequest) {
        log.info("SignInSignUpHandler signIn method called");
        Mono<SignInRequestModel> signInRequestModelMono = serverRequest.bodyToMono(SignInRequestModel.class);
        return signInRequestModelMono.flatMap(signInRequestModel ->
                this.userService.getUserEntityByEmailId(signInRequestModel.getUsername()).flatMap(userDto -> {
                    if (passwordEncoder.matches(signInRequestModel.getPassword(), userDto.getPassword())) {
                        return ServerResponse.ok().body(Mono.justOrEmpty(new SignInResponseModel(jwtUtil.generateToken(signInRequestModel))), SignInResponseModel.class);
                    } else {
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                    }
                })
        ).switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());
    }

    public Mono<ServerResponse> signUp(ServerRequest serverRequest) {
        log.info("SignInSignUpHandler signUp method called");
        Mono<UserDto> savedUserDtoMono = serverRequest.bodyToMono(UserDto.class).flatMap(userDto -> this.userService.createUser(userDto));
        return ServerResponse.ok().body(savedUserDtoMono.flatMap(userDto -> Mono.justOrEmpty(ModelMapperUtil.convertToUserResponseModel(userDto))), UserResponseModel.class);
    }

}
