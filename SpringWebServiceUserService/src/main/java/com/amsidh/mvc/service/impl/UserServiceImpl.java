package com.amsidh.mvc.service.impl;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.repository.user.entity.UserEntity;
import com.amsidh.mvc.repository.user.entity.UserRepository;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.ModelMapperUtil;
import com.amsidh.mvc.util.UniqueIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

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
    public Mono<UserDto> createUser(UserDto userDto) {
        log.info("UserServiceImpl createUser method called");
        String userId = uniqueIdGenerator.getUniqueId();
        userDto.setUserId(userId);
        Mono<UserDto> userDtoMono = userRepository.save(ModelMapperUtil.convertToUserEntity(userDto)).map(ModelMapperUtil::convertToUserDto);
        return userDtoMono;
    }

    @Override
    public Mono<UserDto> getUser(String userId) {
        log.info("UserServiceImpl getUser method called");
        Mono<UserDto> userDtoMono = userRepository.findById(userId)
                .doOnError(exception -> new UserException(""))
                .map(ModelMapperUtil::convertToUserDto);
        return userDtoMono;

    }

    @Override
    public Mono<UserDto> updateUser(String userId, UserDto userDto) {
        log.info("UserServiceImpl updateUser method called");
        Mono<UserDto> monoUserDto = getUser(userId);
        Mono<UserEntity> monoUserEntity = monoUserDto.map(ModelMapperUtil::convertToUserEntity).map(userEntity -> {
            String matchingUserId = Optional.ofNullable(userDto.getUserId()).filter(uid -> uid.equals(userEntity.getUserId())).orElseThrow(() -> new UserException("UserId in uri and body are not matching"));
            log.info(String.format("Updating the user having userId %s", matchingUserId));
            Optional.ofNullable(userDto.getFirstName()).ifPresent(userEntity::setFirstName);
            Optional.ofNullable(userDto.getLastName()).ifPresent(userEntity::setLastName);
            Optional.ofNullable(userDto.getEmailId()).ifPresent(userEntity::setEmailId);
            Optional.ofNullable(userDto.getPassword()).ifPresent(userPassword -> userEntity.setEncryptedPassword(new BCryptPasswordEncoder().encode(userPassword)));
            return userEntity;
        });

        monoUserEntity.subscribe(savedEntity ->
                this.userRepository.save(savedEntity).subscribe(u -> {
                    log.info("User Updated Successfully");
                }));

        return monoUserEntity.map(ModelMapperUtil::convertToUserDto);

    }

    @Override
    public Mono<Void> deleteUser(String userId) {
        log.info("UserServiceImpl's deleteById method");
        return this.userRepository.deleteById(userId);

    }

    @Override
    public Flux<UserDto> getUsers() {
        log.info("UserServiceImpl getUsers method called");
        Flux<UserDto> fluxUserDto = this.userRepository.findAll().map(ModelMapperUtil::convertToUserDto);
        return fluxUserDto;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("UserServiceImpl loadUserByUsername method called");
        Optional<UserEntity> userEntity = userRepository.findByEmailId(userName).blockOptional();
        return userEntity.map(user -> {
            return new User(user.getEmailId(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
        }).orElseThrow(() -> new UserException("Invalid Username/Password"));

    }

    @Override
    public Mono<UserDto> getUserEntityByEmailId(String userName) {
        log.info("UserServiceImpl getUserEntityByEmailId method called");
        Mono<UserDto> userDtoMono = userRepository.findByEmailId(userName).map(ModelMapperUtil::convertToUserDto);
        return userDtoMono;
    }


}
