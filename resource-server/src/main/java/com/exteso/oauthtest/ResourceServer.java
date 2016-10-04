package com.exteso.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableResourceServer
public class ResourceServer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class, args);
    }

    @RequestMapping("/")
    public Map<String, String> home() {
        return Collections.singletonMap("message", "Hello World");
    }
}