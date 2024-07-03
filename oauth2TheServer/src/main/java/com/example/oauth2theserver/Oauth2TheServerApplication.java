package com.example.oauth2theserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
public class Oauth2TheServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2TheServerApplication.class, args);
    }

}
