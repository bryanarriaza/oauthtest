package com.exteso.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
public class ResourceServer {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServer.class, args);
    }

    private String message = "Hello world!";

    @PreAuthorize("#oauth2.hasScope('resource-server-read')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Map<String, Object> home() {
        return Collections.singletonMap("message", message);
    }

    @PreAuthorize("#oauth2.hasScope('resource-server-write')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void updateMessage(@RequestBody String message) {
        this.message = message;
    }

    @PreAuthorize("#oauth2.hasScope('resource-server-read')")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Map<String, String> user(Principal user) {
        return Collections.singletonMap("message", "user is: "+user.toString());
    }

}