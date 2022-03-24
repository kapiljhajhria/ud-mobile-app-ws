package com.example.photoapp.config;

import com.example.photoapp.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;// 10 days
    public static final long EMAIL_VERIFICATION_EXPIRATION_TIME = 86400000; // 1 day
    public static final String TOKEN_PREFIX = "BEARER ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_URL = "/api/v1/login";
    public static final String EMAIL_VERIFICATION_URL = "/api/v1/users/email-verification";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        log.info("is Token secret null:- " + (appProperties.getTokenSecret() == null));
        return appProperties.getTokenSecret();
    }
}