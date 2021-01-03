package com.amsidh.mvc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.repository.user.entity.UserEntity;
import com.amsidh.mvc.repository.user.entity.UserRepository;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.ModelMapperUtil;
import com.amsidh.mvc.util.UniqueIdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UniqueIdGenerator uniqueIdGenerator;
    private final UserRepository userRepository;

    public UserServiceImpl(UniqueIdGenerator uniqueIdGenerator, UserRepository userRepository) {
        log.info("Initializing UserServiceImpl!!!");
        this.uniqueIdGenerator = uniqueIdGenerator;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = uniqueIdGenerator.getUniqueId();
        userDto.setUserId(userId);
        UserEntity userEntity = userRepository.save(ModelMapperUtil.convertToUserEntity(userDto));
        return Optional.ofNullable(userEntity).map(ModelMapperUtil::convertToUserDto).orElseThrow(() -> new UserException("User Not saved in database"));
    }

    @Override
    public UserDto getUser(String userId) {
        return userRepository.findById(userId)
                        .map(ModelMapperUtil::convertToUserDto)
                        .orElseThrow(() -> new UserException("User no found with userId " + userId));

    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        return userRepository.findById(userId).map(userEntity -> {
            Optional.ofNullable(userDto.getUserId()).filter(uid -> uid.equals(userEntity.getUserId())).orElseThrow(() -> new UserException("UserId in uri and body are not matching"));
            Optional.ofNullable(userDto.getFirstName()).ifPresent(userEntity::setFirstName);
            Optional.ofNullable(userDto.getLastName()).ifPresent(userEntity::setLastName);
            Optional.ofNullable(userDto.getEmailId()).ifPresent(userEntity::setEmailId);
            Optional.ofNullable(userDto.getPassword()).ifPresent(userPassword-> userEntity.setEncryptedPassword(new BCryptPasswordEncoder().encode(userPassword)));
            return  userRepository.save(userEntity);
        }).map(ModelMapperUtil::convertToUserDto).orElseThrow(() -> new UserException("User not found with userId " + userId));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.findById(userId).ifPresent(userRepository::delete);
    }

    @Override
    public List<UserDto> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(ModelMapperUtil::convertToUserDto).collect(Collectors.toList());
    }
    
    @Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmailId(userName);
		return Optional.ofNullable(userEntity).map(user -> {
			return new User(user.getEmailId(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
		}).orElseThrow(() -> new UserException("Invalid Username/Password"));

	}
	
    @Override
	public UserDto getUserEntityByEmailId(String userName) {
    	UserEntity userEntity = userRepository.findByEmailId(userName);
    	return Optional.ofNullable(userEntity).map(ModelMapperUtil::convertToUserDto).orElseThrow(() -> new UserException("User not found with emailId "+ userName));
	}
    

}
