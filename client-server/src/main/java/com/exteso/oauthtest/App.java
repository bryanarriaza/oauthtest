package com.exteso.oauthtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SpringBootApplication
@RestController
public class App  {

    @Autowired
    @Qualifier("forServer")
    private OAuth2RestTemplate forServer;

    @Autowired
    @Qualifier("forUser")
    private OAuth2RestTemplate forUser;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @RequestMapping(value = "/secured/")
    public void secured(HttpServletResponse res) throws IOException {
        res.sendRedirect("/secured/index.html");//FIXME find a better way...
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.GET)
    public Map<String, String> getMessage() {
        return forServer.getForObject("http://localhost:9090", Map.class);
    }

    @RequestMapping(value = "/api/message", method = RequestMethod.POST)
    public void saveMessage(@RequestBody String newMessage) {
        forUser.postForLocation("http://localhost:9090", newMessage);
    }

    @Configuration
    public static class OauthClientConfiguration {

        @Bean
        @ConfigurationProperties("resourceServerClient")
        public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
            //we use client credential, as this use case does not need to act on behalf the user
            return new ClientCredentialsResourceDetails();
        }


        @Qualifier("forServer")
        @Bean
        public OAuth2RestTemplate restTemplate() {
            return new OAuth2RestTemplate(clientCredentialsResourceDetails(), new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
        }

        @Qualifier("forUser")
        @Bean
        public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
            return new OAuth2RestTemplate(resource, context);
        }
    }
}
