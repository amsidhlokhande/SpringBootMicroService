package com.amsidh.mvc.util;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.model.model.UserRequestModel;
import com.amsidh.mvc.model.model.UserResponseModel;
import com.amsidh.mvc.repository.user.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModelMapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    static {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public static UserRequestModel convertToUserRequestModel(UserDto userDto) {
        return Optional.ofNullable(userDto).map(user -> modelMapper.map(userDto, UserRequestModel.class)).orElseThrow(() -> new UserException("UserDto should not be null"));
    }

    public static UserDto convertToUserDto(UserRequestModel userRequestModel) {
        return Optional.ofNullable(userRequestModel).map(userModel -> modelMapper.map(userModel, UserDto.class)).orElseThrow(() -> new UserException("UserRequestModel should not be null"));
    }

    public static List<UserResponseModel> convertToUserResponseModels(List<UserDto> userDtos) {
        return userDtos.parallelStream().map(ModelMapperUtil::convertToUserResponseModel).collect(Collectors.toList());
        //return modelMapper.map(userDtos, new TypeToken<List<UserRequestModel>>() {}.getType());
    }

    public static List<UserDto> convertToUserDtos(List<UserRequestModel> userRequestModels) {
        return userRequestModels.parallelStream().map(ModelMapperUtil::convertToUserDto).collect(Collectors.toList());
        //return modelMapper.map(userRequestModels, new TypeToken<List<UserDto>>() {}.getType());
    }

    //Convert UserDto to UserEntity vice versa.

    public static UserDto convertToUserDto(UserEntity userEntity) {
        return Optional.ofNullable(userEntity).map(user -> {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setPassword(PASSWORD_ENCODER.encode(userEntity.getEncryptedPassword()));
            return userDto;
        }).orElseThrow(() -> new UserException("UserEntity should not be null"));
    }

    public static UserEntity convertToUserEntity(UserDto userDto) {
        return Optional.ofNullable(userDto).map(user -> {
            UserEntity userEntity = modelMapper.map(user, UserEntity.class);
            userEntity.setEncryptedPassword(PASSWORD_ENCODER.encode(userDto.getPassword()));
            return userEntity;
        }).orElseThrow(() -> new UserException("UserDto should not be null"));
    }

    public static UserResponseModel convertToUserResponseModel(UserDto userDto) {
    	return Optional.ofNullable(userDto).map(user -> modelMapper.map(userDto, UserResponseModel.class)).orElseThrow(() -> new UserException("UserDto should not be null"));
    }
}
