package com.karg.userrsmanagement.config;

import com.karg.userrsmanagement.SpringApplicationContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "BEARER ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/users";
    public static final String LOGIN_URL = "/api/v1/login";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        log.info("is Token secret null:- " + (appProperties.getTokenSecret() == null));
        return appProperties.getTokenSecret();
    }
}