package com.amsidh.mvc.service;

import com.amsidh.mvc.model.model.User;

public interface UserService {

	User createUser(User user);

	User getUser(String userId);

	User updateUser(String userId, User user);

	void deleteUser(String userId);

	Iterable<User> getUsers();
}
