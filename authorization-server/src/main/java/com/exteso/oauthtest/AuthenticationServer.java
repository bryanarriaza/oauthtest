package com.exteso.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 */
@SpringBootApplication
@SessionAttributes("authorizationRequest")
public class AuthenticationServer {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServer.class, args);
    }

}
