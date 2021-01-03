package com.amsidh.mvc.controller;

import com.amsidh.mvc.model.model.UserDto;
import com.amsidh.mvc.model.model.UserRequestModel;
import com.amsidh.mvc.model.model.UserResponseModel;
import com.amsidh.mvc.service.UserService;
import com.amsidh.mvc.util.ModelMapperUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @GetMapping(value = "/health/check")
    public ResponseEntity<String> healthCheck() {
        log.info("Users web service is working on port " + environment.getProperty("local.server.port"));
        return ResponseEntity.ok("Users web service is working on port " + environment.getProperty("local.server.port"));
    }


    @ApiOperation(value = "User Create Documentation is here", notes = "Note  about user POST API", response = UserRequestModel.class)
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, consumes = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userRequestModel) {

        log.info("Creating user with " + userRequestModel);
        UserDto userDto = this.userService.createUser(ModelMapperUtil.convertToUserDto(userRequestModel));
        return new ResponseEntity<>(ModelMapperUtil.convertToUserResponseModel(userDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "User Get Documentation is here", notes = "Note  about user GET API")
    @GetMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
        log.info("Getting user for userId " + userId);
        return ResponseEntity.ok(ModelMapperUtil.convertToUserResponseModel(userService.getUser(userId)));
    }

    @ApiOperation(value = "Get all sser documentation is here", notes = "Note  about get all user API")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserResponseModel>> getUsers() {
        log.info("Getting all users");
        return ResponseEntity.ok(ModelMapperUtil.convertToUserResponseModels(this.userService.getUsers()));
    }

    @ApiOperation(value = "User Put Documentation is here", notes = "Note  about user PUT API")
    @PutMapping(value = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> updateUser(@PathVariable String userId, @Valid @RequestBody UserRequestModel userRequestModel) {
        log.info("Updating user with userId " + userId + " user details : " + userRequestModel);
        return new ResponseEntity<>(ModelMapperUtil.convertToUserResponseModel(userService.updateUser(userId, ModelMapperUtil.convertToUserDto(userRequestModel))), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "User delete Documentation is here", notes = "Note  about user DELETE API")
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        log.info("Deleting user with userId " + userId);
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
