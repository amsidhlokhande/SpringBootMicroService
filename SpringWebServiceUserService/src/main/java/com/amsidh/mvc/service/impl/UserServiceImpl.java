package com.amsidh.mvc.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.amsidh.mvc.exception.UserException;
import com.amsidh.mvc.model.model.User;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.UniqueIdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UniqueIdGenerator uniqueIdGenerator;

	private static final Map<String, User> users = new HashMap<>();

	public UserServiceImpl(UniqueIdGenerator uniqueIdGenerator) {
		log.info("Initializing UserServiceImpl!!!");
		this.uniqueIdGenerator = uniqueIdGenerator;
	}

	@Override
	public User createUser(User user) {
		String userId = uniqueIdGenerator.getUniqueId();
		user.setUserId(userId);
		users.put(userId, user);
		return user;
	}

	@Override
	public User getUser(String userId) {
		return Optional.ofNullable(users.get(userId))
				.orElseThrow(() -> new UserException("User no found with userId " + userId));

	}

	@Override
	public User updateUser(String userId, User user) {
		User existingUser = getUser(userId);
		return Optional.ofNullable(existingUser).map(u -> {
			Optional.ofNullable(user.getUserId()).filter(uid-> uid.equals(u.getUserId())).orElseThrow(()-> new UserException("UserId in uri and body are not matching"));
			Optional.ofNullable(user.getFirstName()).ifPresent(u::setFirstName);
			Optional.ofNullable(user.getLastName()).ifPresent(u::setLastName);
			Optional.ofNullable(user.getEmailId()).ifPresent(u::setEmailId);
			Optional.ofNullable(user.getPassword()).ifPresent(u::setPassword);
			return u;
		}).orElseThrow(() -> new UserException("User not found with userId " + userId));

	}

	@Override
	public void deleteUser(String userId) {
		User deleteUser = Optional
				.ofNullable(users.get(Optional.ofNullable(userId)
						.orElseThrow(() -> new UserException("UserId must not be empty or null"))))
				.orElseThrow(() -> new UserException("User not found with userId " + userId));
		users.remove(userId, deleteUser);
	}

	@Override
	public Iterable<User> getUsers() {
		return users.values();
	}

}
