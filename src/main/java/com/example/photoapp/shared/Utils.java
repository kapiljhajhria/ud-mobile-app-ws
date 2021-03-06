package com.example.photoapp.shared;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.photoapp.config.SecurityConstants;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generatePasswordResetToken(String userId) {

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.getTokenSecret()));
    }
    // private final int ITERATIONS = 10000;
    // private final int KEY_LENGTH = 256;

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String generateAddressId(int i) {
        return generateRandomString(i);
    }

    public Boolean hasTokenExpired(String token) {

        // jjwt decode jwt and check if token is expired
        Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.getTokenSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            Date tokenExpirationDate = jwt.getExpiresAt();
            Date todayDate = new Date();

            return tokenExpirationDate.before(todayDate);
        } catch (TokenExpiredException ex) {
            return true;
        }
    }

    public String generateEmailVerificationToken(String userId) {
        /// TODO : make compact token and use that for email verification
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(
                        new Date(System.currentTimeMillis() + SecurityConstants.EMAIL_VERIFICATION_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.getTokenSecret()));
    }
}