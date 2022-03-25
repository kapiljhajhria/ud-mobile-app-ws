package com.example.photoapp.service;

import com.example.photoapp.entity.AddressEntity;
import com.example.photoapp.entity.UserEntity;
import com.example.photoapp.exception.UserServiceException;
import com.example.photoapp.repository.UserRepository;
import com.example.photoapp.shared.AmazonSES;
import com.example.photoapp.shared.Utils;
import com.example.photoapp.shared.dto.AddressDto;
import com.example.photoapp.shared.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class UserServiceImplTest {


    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    AmazonSES amazonSES;

    @InjectMocks
    UserServiceImpl userService;


    UserEntity userEntity01 = new UserEntity();
    UserEntity userEntity02 = new UserEntity();

    //    AddressDto addressDto01 = new AddressDto();
//    AddressDto addressDto02 = new AddressDto();
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        //userEntity stub

        userEntity01.setId(1L);
        userEntity01.setFirstName("kapilFirstName");
        userEntity01.setLastName("kapilLastName");
        userEntity01.setUserId("kapil_publicUserId");
        userEntity01.setEncryptedPassword("kapil_encryptedPassword");
        userEntity01.setEmail("jhajhria44@gmail.com");
        userEntity01.setEmailVerificationToken("kapil_emailVerificationToken");
        userEntity01.setAddresses(getAddressesEntity());

        userEntity02.setId(2L);
        userEntity02.setFirstName("ajayFirstName");
        userEntity02.setLastName("ajayLastName");
        userEntity02.setUserId("ajay_publicUserId");
        userEntity02.setEncryptedPassword("ajay_encryptedPassword");
        userEntity02.setEmail("kapil.jhajhria@techverito.com");
        userEntity02.setEmailVerificationToken("ajay_emailVerificationToken");
        userEntity02.setAddresses(getAddressesEntity());


//        addressDto01.setCity("delhi");
//        addressDto01.setCountry("India");
//        addressDto01.setStreetName("Nehru Street");
//        addressDto01.setPostalCode("110092");
//        addressDto01.setType("shipping");
//
//        addressDto02.setCity("Kanpur");
//        addressDto02.setCountry("India");
//        addressDto02.setStreetName("Food Street");
//        addressDto02.setPostalCode("120012");
//        addressDto02.setType("billing");

    }


    @Test
    void getUser() {


        when(userRepository.findByEmail(anyString())).thenReturn(userEntity01);

        UserDto user = userService.getUser("jhajhria44@gmail.com");

        Assertions.assertNotNull(user);
        assert (user.getFirstName().equals(userEntity01.getFirstName()));
        assert (user.getUserId().equals(userEntity01.getUserId()));
        assert (user.getEncryptedPassword().equals(userEntity01.getEncryptedPassword()));
    }

    @Test
    void whenGetUserWithInvalidEmail_thenUserNameNotFoundException() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
                    userService.getUser("testEmail");

                }
        );
    }

    @Test
    void whenCreateUserWithValidEmailCreateNewUser() {
        //no current user with given email id
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("testPublicAddressId");
        when(utils.generateUserId(anyInt())).thenReturn("testPublicUserId");
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("testEncryptedPassword");
//        when(utils.generateEmailVerificationToken(anyString())).thenReturn("testEmailVerificationToken");
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity01);


        List<AddressDto> addressDtoList = getAddressDtoList();


        UserDto userDto = new UserDto();
        userDto.setAddresses(addressDtoList);
        userDto.setEmail("jhajhria44@gmail.com");
        userDto.setFirstName("kapilFirstName");
        userDto.setLastName("kapilLastName");
        userDto.setPassword("kapilPassword");


        UserDto storedUserDetails = userService.createUser(userDto);

        Assertions.assertNotNull(storedUserDetails);
        Assertions.assertEquals(userEntity01.getFirstName(), storedUserDetails.getFirstName());
        Assertions.assertEquals(userEntity01.getLastName(), storedUserDetails.getLastName());
        Assertions.assertNotNull(storedUserDetails.getUserId());
        Assertions.assertEquals(storedUserDetails.getAddresses().size(), userEntity01.getAddresses().size());
        verify(utils, times(storedUserDetails.getAddresses().size())).generateAddressId(anyInt());
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));

    }

    @Test
    void whenCreateUserWithExistingEmail_thenThrowUserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity01);

        List<AddressDto> addressDtoList = getAddressDtoList();


        UserDto userDto = new UserDto();
        userDto.setAddresses(addressDtoList);
        userDto.setEmail("jhajhria44@gmail.com");
        userDto.setFirstName("kapilFirstName");
        userDto.setLastName("kapilLastName");
        userDto.setPassword("kapilPassword");


        Assertions.assertThrows(UserServiceException.class, () -> {
                    userService.createUser(userDto);
                }
        );

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

    private List<AddressEntity> getAddressesEntity() {
        List<AddressEntity> addressEntityList = new ArrayList<>();
        List<AddressDto> addressDtoList = getAddressDtoList();
        Type listType = new TypeToken<List<AddressDto>>() {
        }.getType();
        return new ModelMapper().map(addressDtoList, listType);
    }
}