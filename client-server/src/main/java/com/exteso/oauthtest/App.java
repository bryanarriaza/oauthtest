package com.exteso.oauthtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
@EnableOAuth2Client
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @RequestMapping(value = "/api/message", method = RequestMethod.GET)
    public Map<String, String> getMessage() {
        return Collections.singletonMap("message", "Hello world");//FIXME, delegate to resource server
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public void saveMessage(@RequestBody String newMessage) {
        //FIXME, this method will require consent screen and the user will have the scope for writing the message
    }

    @Configuration
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/**").permitAll();
        }
    }

}
