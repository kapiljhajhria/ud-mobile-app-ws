package com.karg.userrsmanagement.controller;


import com.karg.userrsmanagement.service.UserService;
import com.karg.userrsmanagement.shared.dto.UserDto;
import com.karg.userrsmanagement.ui.model.request.UserDetailsRequestModel;
import com.karg.userrsmanagement.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(userId);

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.ok().body(returnValue);
    }

    @PostMapping
    public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);


        return ResponseEntity.created(null).body(returnValue);
    }

    @PutMapping
    public String updateUser() {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }
}