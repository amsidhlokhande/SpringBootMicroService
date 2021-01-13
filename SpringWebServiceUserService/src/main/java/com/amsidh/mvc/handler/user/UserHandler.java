package com.amsidh.mvc.handler.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amsidh.mvc.feign.client.album.AlbumsServiceClient;
import com.amsidh.mvc.feign.client.album.model.AlbumResponseModel;
import com.amsidh.mvc.handler.user.model.UserRequestModel;
import com.amsidh.mvc.handler.user.model.UserResponseModel;
import com.amsidh.mvc.repository.user.entity.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class UserHandler {

	private final UserRepository userRepository;
	private final ModelMapper modelMapper;
	private final AlbumsServiceClient albumsServiceClient;

	public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
		log.info("UserHandler getAllUsers method called");
		return ServerResponse.ok().body(userRepository.findAll().flatMap(userEntity -> {
			return Mono.justOrEmpty(modelMapper.map(userEntity, UserResponseModel.class));
		}).flatMap(userResponse->getAlbumsOfUser(userResponse)), UserResponseModel.class);
	}

	public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
		log.info("UserHandler getUserById method called");
		return ServerResponse.ok()
				.body(userRepository.findById(serverRequest.pathVariable("userId")).flatMap(userEntity -> {
					return Mono.justOrEmpty(modelMapper.map(userEntity, UserResponseModel.class))
							.flatMap(userResponse->getAlbumsOfUser(userResponse));
				}), UserResponseModel.class);
	}

	public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
		log.info("UserHandler updateUser method called");

		return ServerResponse.ok().body(serverRequest.bodyToMono(UserRequestModel.class).flatMap(userRequestModel -> {
			return userRepository.findById(serverRequest.pathVariable("userId")).flatMap(userEntity -> {
				Optional.ofNullable(userRequestModel.getFirstName()).ifPresent(userEntity::setFirstName);
				Optional.ofNullable(userRequestModel.getLastName()).ifPresent(userEntity::setLastName);
				Optional.ofNullable(userRequestModel.getEmailId()).ifPresent(userEntity::setEmailId);
				return userRepository.save(userEntity).flatMap(updatedUserEntity -> {
					return Mono.justOrEmpty(modelMapper.map(updatedUserEntity, UserResponseModel.class)).flatMap(userResponse->getAlbumsOfUser(userResponse));
				});

			});
		}), UserResponseModel.class);

	}

	private Mono<? extends UserResponseModel> getAlbumsOfUser(UserResponseModel userResponse) {
		Mono<AlbumResponseModel> albums = albumsServiceClient
				.getAlbumsByUserId(userResponse.getUserId());
		userResponse.setAlbums(albums);
		return Mono.justOrEmpty(userResponse);
	}

	public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
		log.info("UserHandler deleteUserById method called");
		return ServerResponse.ok().body(userRepository.deleteById(serverRequest.pathVariable("userId")), Void.class);
	}

}
