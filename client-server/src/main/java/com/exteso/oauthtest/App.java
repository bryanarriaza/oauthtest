package com.exteso.oauthtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class App  {

    @Autowired
    private OAuth2RestTemplate forServer;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.GET)
    public Map<String, String> getMessage() {
        return forServer.getForObject("http://localhost:9090", Map.class);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public void saveMessage(@RequestBody String newMessage) {
        forServer.postForLocation("http://localhost:9090", newMessage);
    }

    @Configuration
    public static class OauthClientConfiguration {


        @Bean
        public OAuth2RestTemplate restTemplate() {

            //we use client credential, as this use case does not need to act on behalf the user
            ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

            //FIXME remove hardcoded data :)
            resource.setAccessTokenUri("http://localhost:8080/auth/oauth/token");
            resource.setClientId("service-account-1");
            resource.setClientSecret("service-account-1-secret");

            AccessTokenRequest atr = new DefaultAccessTokenRequest();
            return new OAuth2RestTemplate(resource, new DefaultOAuth2ClientContext(atr));
        }


    }
}
