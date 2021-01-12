package com.amsidh.mvc.util;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.AlbumResponseModel;
import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.model.model.UserRequestModel;
import com.amsidh.mvc.model.model.UserResponseModel;
import com.amsidh.mvc.repository.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class ModelMapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final RestTemplate restTemplate;
    private final Environment environment;

    static {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public UserRequestModel convertToUserRequestModel(UserDto userDto) {
        return Optional.ofNullable(userDto).map(user -> modelMapper.map(userDto, UserRequestModel.class)).orElseThrow(() -> new UserException("UserDto should not be null"));
    }

    public UserDto convertToUserDto(UserRequestModel userRequestModel) {
        return Optional.ofNullable(userRequestModel).map(userModel -> modelMapper.map(userModel, UserDto.class)).orElseThrow(() -> new UserException("UserRequestModel should not be null"));
    }

    public List<UserResponseModel> convertToUserResponseModels(List<UserDto> userDtos) {
        return userDtos.parallelStream().map(this::convertToUserResponseModel).collect(Collectors.toList());
        //return modelMapper.map(userDtos, new TypeToken<List<UserRequestModel>>() {}.getType());
    }

    public List<UserDto> convertToUserDtos(List<UserRequestModel> userRequestModels) {
        return userRequestModels.parallelStream().map(this::convertToUserDto).collect(Collectors.toList());
        //return modelMapper.map(userRequestModels, new TypeToken<List<UserDto>>() {}.getType());
    }

    //Convert UserDto to UserEntity vice versa.

    public UserDto convertToUserDto(UserEntity userEntity) {
        return Optional.ofNullable(userEntity).map(user -> {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setPassword(userEntity.getEncryptedPassword());
            return userDto;
        }).orElseThrow(() -> new UserException("UserEntity should not be null"));
    }

    public UserEntity convertToUserEntity(UserDto userDto) {
        return Optional.ofNullable(userDto).map(user -> {
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            userEntity.setEncryptedPassword(PASSWORD_ENCODER.encode(userDto.getPassword()));
            return userEntity;
        }).orElseThrow(() -> new UserException("UserDto should not be null"));
    }

    @LoadBalanced
    public UserResponseModel convertToUserResponseModel(UserDto userDto) {
        return Optional.ofNullable(userDto).map(user -> {

            //Calling Albums-WS service to get albums for a user
            ResponseEntity<List<AlbumResponseModel>> exchange = restTemplate.exchange(String.format(environment.getProperty("albums.url.user"), user.getUserId()), HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
            });

            UserResponseModel userResponseModel = modelMapper.map(userDto, UserResponseModel.class);
            userResponseModel.setAlbums(exchange.getBody());
            return userResponseModel;

        }).orElseThrow(() -> new UserException("UserDto should not be null"));
    }
}
