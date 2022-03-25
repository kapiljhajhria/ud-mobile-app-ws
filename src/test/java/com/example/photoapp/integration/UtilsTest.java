package com.example.photoapp.integration;

import com.example.photoapp.shared.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGenerateUserId() {
        String userId01 = utils.generateUserId(30);
        String userId02 = utils.generateUserId(30);

        Assertions.assertNotNull(userId01);
        Assertions.assertNotNull(userId02);

        Assertions.assertTrue(userId01.length() == 30);
        Assertions.assertTrue(userId02.length() == 30);

        Assertions.assertNotEquals(userId01, userId02);
    }

    @Test
    public void testHasTokenNotExpired() {
        String token = utils.generateEmailVerificationToken("skjdfsu387d237jkgwf");
        Assertions.assertNotNull(token);

        boolean hasTokenExpired = utils.hasTokenExpired(token);

        Assertions.assertFalse(hasTokenExpired);

    }

    @Test
    public void testHasTokenExpired() {
        String expiredToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJza2pkZnN1Mzg3ZDIzN2prZ3dmIiwiZXhwIjoxNjQ4MjExOTA3fQ.dBtRJ5yOyv_HFUZKt9GkU9uPAbepoyFezO9_cUR5EMhuKpBtWbPGU1hCvi9vF4BkkhclXCt9RYWSIiu0wH5oBA";
        Assertions.assertNotNull(expiredToken);

        boolean hasTokenExpired = utils.hasTokenExpired(expiredToken);

        Assertions.assertTrue(hasTokenExpired);

    }
}