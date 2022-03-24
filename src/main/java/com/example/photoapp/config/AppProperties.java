package com.example.photoapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class AppProperties {

    @Autowired
    private Environment env;

    public String getProperty(String key) {
        return env.getProperty(key);
    }

    public String getTokenSecret() {
        return getProperty("tokenSecret");
    }
}