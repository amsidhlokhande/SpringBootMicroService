package com.amsidh.mvc.handlers.signup;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.handlers.signup.model.SignUpRequestModel;
import com.amsidh.mvc.handlers.signup.model.SignUpResponseModel;
import com.amsidh.mvc.repository.user.entity.UserEntity;
import com.amsidh.mvc.repository.user.entity.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class SignUpHandler {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	public Mono<ServerResponse> signUp(ServerRequest serverRequest) {
		log.debug("SignInSignUpHandler signUp method called");
		
	return	serverRequest.bodyToMono(SignUpRequestModel.class).flatMap(signUpRequestModel->{
			UserEntity userEntity = modelMapper.map(signUpRequestModel, UserEntity.class);
			userEntity.setUserId(UUID.randomUUID().toString());
			userEntity.setEncryptedPassword(passwordEncoder.encode(signUpRequestModel.getPassword()));
			return userRepository.save(userEntity);
		}).flatMap(userEntity->{
			return ServerResponse.ok().body(Mono.just(modelMapper.map(userEntity, SignUpResponseModel.class)), SignUpResponseModel.class);
		});
		
	}
}
