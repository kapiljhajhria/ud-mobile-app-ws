package com.karg.userrsmanagement.controller;


import com.karg.userrsmanagement.exception.ErrorMessages;
import com.karg.userrsmanagement.exception.UserServiceException;
import com.karg.userrsmanagement.service.UserService;
import com.karg.userrsmanagement.shared.dto.UserDto;
import com.karg.userrsmanagement.ui.model.request.UserDetailsRequestModel;
import com.karg.userrsmanagement.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    // first media type will be default
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(userId);

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.ok().body(returnValue);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);


        return ResponseEntity.created(null).body(returnValue);
    }

    @PutMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> updateUser(@PathVariable String userId, @Valid @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        log.info("UserDto: " + userDto);
        UserDto updatedUser = userService.updateUser(userId, userDto);
        log.info("User updated successfully ,{}", updatedUser);
        BeanUtils.copyProperties(updatedUser, returnValue);
        return ResponseEntity.ok(returnValue);
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String deleteUser() {
        return "delete user was called";
    }
}