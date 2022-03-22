package com.karg.userrsmanagement.controller;


import com.karg.userrsmanagement.exception.ErrorMessages;
import com.karg.userrsmanagement.exception.UserServiceException;
import com.karg.userrsmanagement.service.AddressesService;
import com.karg.userrsmanagement.service.UserService;
import com.karg.userrsmanagement.shared.dto.AddressDto;
import com.karg.userrsmanagement.shared.dto.UserDto;
import com.karg.userrsmanagement.ui.model.request.RequestOperationName;
import com.karg.userrsmanagement.ui.model.request.UserDetailsRequestModel;
import com.karg.userrsmanagement.ui.model.response.AddressesRest;
import com.karg.userrsmanagement.ui.model.response.OperationStatusModel;
import com.karg.userrsmanagement.ui.model.response.RequestOperationStatus;
import com.karg.userrsmanagement.ui.model.response.UserRest;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressesService addressesService;

    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    // first media type will be default
    public ResponseEntity<UserRest> getUser(@PathVariable String userId) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(userId);

        BeanUtils.copyProperties(userDto, returnValue);

        return ResponseEntity.ok().body(returnValue);
    }

    @GetMapping()
    public ResponseEntity<List<UserRest>> getAllUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserDto> users = userService.getUsers(page, limit);

        List<UserRest> returnValue = new ArrayList<>();

        for (UserDto userDto : users) {
            log.info("User details: {}", userDto);
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return ResponseEntity.ok().body(returnValue);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails) {

        if (userDetails.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails, userDto);
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);//(source, destination)

        UserDto createdUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createdUser, returnValue);
        UserRest returnValue = modelMapper.map(createdUser, UserRest.class);

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

    @DeleteMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<OperationStatusModel> deleteUser(@PathVariable String userId) {
        OperationStatusModel returnValue = new OperationStatusModel();
        userService.deleteUser(userId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return ResponseEntity.ok().body(returnValue);
    }

    @GetMapping(path = "/{userId}/addresses", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<AddressesRest>> getUserAddresses(@PathVariable String userId) {
        List<AddressesRest> returnValue = new ArrayList<>();

        List<AddressDto> addressDTOs = addressesService.getUserAddresses(userId);

        if (addressDTOs != null && !addressDTOs.isEmpty()) {
            java.lang.reflect.Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            returnValue = new ModelMapper().map(addressDTOs, listType);
        }

        return ResponseEntity.ok().body(returnValue);
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<EntityModel<AddressesRest>> getUserAddresses(@PathVariable String userId, @PathVariable String addressId) {


        AddressDto addressDTOs = addressesService.getUserAddress(addressId);

        AddressesRest returnValue = new ModelMapper().map(addressDTOs, AddressesRest.class);

        //http://localhost:8080/users/{userId}
        Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
        //http://localhost:8080/users/{userId}/addresses
        Link userAddressesLink = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(userId)
                .slash("addresses")
                .withRel("addresses");
        //http://localhost:8080/users/{userId}/addresses/{addressId
        Link selfLink = WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(userId)
                .slash("addresses")
                .slash(addressId)
                .withRel("self");

//        returnValue.add(userLink);
//        returnValue.add(userAddressesLink);
//        returnValue.add(selfLink);

        EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink));

//        return ResponseEntity.ok().body(returnValue);
        return ResponseEntity.ok().body(EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink)));
    }
}