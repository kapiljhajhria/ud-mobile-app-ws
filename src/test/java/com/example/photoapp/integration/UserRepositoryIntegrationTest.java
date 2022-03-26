package com.example.photoapp.integration;

import com.example.photoapp.entity.AddressEntity;
import com.example.photoapp.entity.UserEntity;
import com.example.photoapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void setUp() {

        //setup userEntity

        userEntity.setEmail("john@email.com");
        userEntity.setEncryptedPassword("password");
        userEntity.setEmailVerificationStatus(Boolean.TRUE);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setUserId("1a2b3c4d5e6f7g8h9i0j");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("New York");
        addressEntity.setCountry("USA");
        addressEntity.setAddressId("1a2b3c4d5e6f7g8h9i0jsda");
        addressEntity.setUserDetails(userEntity);
        addressEntity.setType("shipping");
        addressEntity.setStreetName("Wall street");
        addressEntity.setPostalCode("10001");

        List<AddressEntity> addressEntities = new ArrayList<>();
        addressEntities.add(addressEntity);
        userEntity.setAddresses(addressEntities);

        userRepository.save(userEntity);

    }

    @Test
    void findALlUsersWithConfirmedEmailAddress() {

        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> page = userRepository.findALlUsersWithConfirmedEmailAddress(pageableRequest);


        List<UserEntity> userEntities = page.getContent();

        assertNotNull(userEntities);
        assertEquals(1, userEntities.size());

    }


}