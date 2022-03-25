package com.example.photoapp.controller;

import com.example.photoapp.service.AddressesService;
import com.example.photoapp.service.UserService;
import com.example.photoapp.shared.dto.AddressDto;
import com.example.photoapp.shared.dto.UserDto;
import com.example.photoapp.ui.model.response.UserRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    AddressesService addressesService;

    @InjectMocks
    UserController userController;


    UserDto userDto;
    final String USER_ID = "jhasfugkjebfua23hhdsf";


    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setFirstName("Kapil");
        userDto.setLastName("Jha");
        userDto.setEmail("jhajhria@gmail.com");
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationToken(null);
        userDto.setEncryptedPassword("xyp23kxfh");
        userDto.setAddresses(getAddressDtoList());
        userDto.setUserId(USER_ID);


        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUser() {
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);


        ResponseEntity<UserRest> response = userController.getUser(USER_ID);
        UserRest userRest = response.getBody();

        Assertions.assertNotNull(userRest);
        Assertions.assertEquals(userDto.getFirstName(), userRest.getFirstName());
        Assertions.assertEquals(userDto.getLastName(), userRest.getLastName());
        Assertions.assertEquals(userDto.getUserId(), userRest.getUserId());

    }

    @Test
    void createUser() {
    }

    private List<AddressDto> getAddressDtoList() {
        List<AddressDto> addressDtoList = new ArrayList<>();

        AddressDto addressDto01 = new AddressDto();
        AddressDto addressDto02 = new AddressDto();

        addressDto01.setCity("delhi");
        addressDto01.setCountry("India");
        addressDto01.setStreetName("Nehru Street");
        addressDto01.setPostalCode("110092");
        addressDto01.setType("shipping");

        addressDto02.setCity("Kanpur");
        addressDto02.setCountry("India");
        addressDto02.setStreetName("Food Street");
        addressDto02.setPostalCode("120012");
        addressDto02.setType("billing");

        addressDtoList.add(addressDto01);
        addressDtoList.add(addressDto02);
        return addressDtoList;
    }
}