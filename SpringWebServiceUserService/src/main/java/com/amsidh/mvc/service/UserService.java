package com.amsidh.mvc.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.amsidh.mvc.model.model.UserDto;

public interface UserService extends UserDetailsService{

    UserDto createUser(UserDto user);

    UserDto getUser(String userId);

    UserDto updateUser(String userId, UserDto user);

    void deleteUser(String userId);

    List<UserDto> getUsers();
    
    public UserDto getUserEntityByEmailId(String userName);
}
