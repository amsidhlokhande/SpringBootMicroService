package com.amsidh.mvc.handler.signin;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handler.signin.model.SignInRequestModel;
import com.amsidh.mvc.handler.signin.model.SignInResponseModel;
import com.amsidh.mvc.repository.user.entity.UserRepository;
import com.amsidh.mvc.security.JwtUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class SignInHandler {
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public Mono<ServerResponse> signIn(ServerRequest serverRequest) {
		log.info("SignInSignUpHandler signIn method called");

		return serverRequest.bodyToMono(SignInRequestModel.class).flatMap(signInRequestModel ->

		this.userRepository.findByEmailId(signInRequestModel.getUsername()).flatMap(userEntity -> {
			if (passwordEncoder.matches(signInRequestModel.getPassword(), userEntity.getEncryptedPassword())) {
				return ServerResponse.ok().body(
						Mono.justOrEmpty(new SignInResponseModel(jwtUtil.generateToken(signInRequestModel))),
						SignInResponseModel.class);
			} else {
				return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
			}
		})).switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build());

	}

}
