package com.amsidh.mvc.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amsidh.mvc.model.model.User;
import com.amsidh.mvc.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api("User Rest API Documentation")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
    private final Environment environment;
	public UserController(UserService userService, Environment environment) {
		log.info("Initializing UserController!!!");
		this.userService = userService;
		this.environment = environment;
	}

	@ApiOperation(value = "User Get Documentation is here", notes = "Note  about user GET API")
	@GetMapping(value="/health/check")
	public ResponseEntity<String> healthCheck() {
		log.info("Users web service is working on port " + environment.getProperty("local.server.port"));
		return ResponseEntity.ok("Users web service is working on port " + environment.getProperty("local.server.port"));
	}
	
	
	@ApiOperation(value = "User Create Documentation is here", notes = "Note  about user POST API", response = User.class)
	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		
		log.info("Creating user with " + user);
		return new ResponseEntity<>(this.userService.createUser(user), HttpStatus.CREATED);
	}

	@ApiOperation(value = "User Get Documentation is here", notes = "Note  about user GET API")
	@GetMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> getUser(@PathVariable String userId) {
		log.info("Getting user for userId " + userId);
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@ApiOperation(value = "Get all sser documentation is here", notes = "Note  about get all user API")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<User>> getUsers() {
		log.info("Getting all users");
		
		List<User> users = StreamSupport
		  .stream(userService.getUsers().spliterator(), false)
		  .collect(Collectors.toList());
		return ResponseEntity.ok(users);
	}

	@ApiOperation(value = "User Put Documentation is here", notes = "Note  about user PUT API")
	@PutMapping(value = "/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<User> updateUser(@PathVariable String userId, @Valid @RequestBody User user) {
		log.info("Updating user with userId " + userId + " user details : " + user);
		return new ResponseEntity<>(userService.updateUser(userId, user), HttpStatus.ACCEPTED);
	}

	@ApiOperation(value = "User delete Documentation is here", notes = "Note  about user DELETE API")
	@DeleteMapping(value = "/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
		log.info("Deleting user with userId " + userId);
		this.userService.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
