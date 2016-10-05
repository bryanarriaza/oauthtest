package com.exteso.oauthtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
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
    private OAuth2RestOperations restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }




    @RequestMapping(value = "/api/message", method = RequestMethod.GET)
    public Map<String, String> getMessage() {
        return restTemplate.getForObject("http://localhost:9090", Map.class);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public void saveMessage(@RequestBody String newMessage) {
        //FIXME, this method will require consent screen and the user will have the scope for writing the message
    }

    @Configuration
    public static class OauthClientConfiguration {
        @Bean
        protected OAuth2ProtectedResourceDetails resource() {

            //we use client credential, as this use case does not need to act on behalf the user
            ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();

            //FIXME remove hardcoded data :)
            resource.setAccessTokenUri("http://localhost:8080/oauth/token");
            resource.setClientId("client-server-s2s");
            resource.setClientSecret("client-server-s2s-secret");

            return resource;
        }

        @Bean
        public OAuth2RestOperations restTemplate() {
            AccessTokenRequest atr = new DefaultAccessTokenRequest();
            return new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
        }
    }
}
