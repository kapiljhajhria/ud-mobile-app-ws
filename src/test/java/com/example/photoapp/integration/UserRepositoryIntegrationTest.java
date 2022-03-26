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

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;

    UserEntity userEntity01 = new UserEntity();
    UserEntity userEntity02 = new UserEntity();

    static boolean recordsCreated = false;

    @BeforeEach
    void setUp() {

        if (!recordsCreated) {
            createRecords();
        }

    }


    @Test
    void findALlUsersWithConfirmedEmailAddress() {

        Pageable pageableRequest = PageRequest.of(0, 2);
        Page<UserEntity> page = userRepository.findALlUsersWithConfirmedEmailAddress(pageableRequest);


        List<UserEntity> userEntities = page.getContent();

        assertNotNull(userEntities);
        assertEquals(1, userEntities.size());

    }

    @Test
    public void testFindUsersByFirstName() {
        String firstName = "John";

        List<UserEntity> userEntities = userRepository.findAllUsersByFirstName(firstName);

        assertNotNull(userEntities);
        assertEquals(2, userEntities.size());
    }

    @Test
    public void testFindUsersByLastName() {
        String lastName = "Doe";

        List<UserEntity> userEntities = userRepository.findAllUsersByLastName(lastName);

        assertNotNull(userEntities);
        assertEquals(2, userEntities.size());
    }

    @Test
    public void testFindUserByKeyword() {
        String keyword = "oh";

        List<UserEntity> users = userRepository.findAllUsersByKeyword(keyword);

        assertNotNull(users);
        assertEquals(2, users.size());

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().contains(keyword));
    }

    @Test
    public void testFindUserByKeywordAndGetObjects() {
        String keyword = "oh";

        List<Object[]> users = userRepository.findAllUserFirstNameAndLastNamesByKeyword(keyword);

        assertNotNull(users);
        assertEquals(2, users.size());

        Object[] user = users.get(0);
        String userFirstName = (String) user[0];
        String userLastName = (String) user[1];
        assertTrue(userFirstName.contains(keyword));
    }

    private void createRecords() {
        //setup userEntity

        userEntity01.setEmail("john1@email.com");
        userEntity01.setEncryptedPassword("password");
        userEntity01.setEmailVerificationStatus(Boolean.TRUE);
        userEntity01.setFirstName("John");
        userEntity01.setLastName("Doe");
        userEntity01.setUserId("1a2b3c4d5e6f7g8h9i0j");

        userEntity02.setEmail("john2@email.com");
        userEntity02.setEncryptedPassword("password");
        userEntity02.setEmailVerificationStatus(Boolean.FALSE);
        userEntity02.setFirstName("John");
        userEntity02.setLastName("Doe");
        userEntity02.setUserId("1a2b3c4d5dfdfe6f7g8h9i0j");


        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("New York");
        addressEntity.setCountry("USA");
        addressEntity.setAddressId("1a2b3c4d5e6f7g8h9i0jsda");
        addressEntity.setUserDetails(userEntity01);
        addressEntity.setType("shipping");
        addressEntity.setStreetName("Wall street");
        addressEntity.setPostalCode("10001");

        List<AddressEntity> addressEntities = new ArrayList<>();
        addressEntities.add(addressEntity);
        userEntity01.setAddresses(addressEntities);
//        userEntity02.setAddresses(addressEntities);

        userRepository.save(userEntity01);
        userRepository.save(userEntity02);

        recordsCreated = true;
    }

}