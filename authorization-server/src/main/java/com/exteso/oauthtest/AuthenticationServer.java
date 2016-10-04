package com.exteso.oauthtest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
@SpringBootApplication
@RestController
@EnableResourceServer
public class AuthenticationServer {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServer.class, args);
    }
    private static final Log logger = LogFactory.getLog(AuthenticationServer.class);

    private AtomicInteger count = new AtomicInteger(0);

    @RequestMapping("/user")
    public Principal user(Principal user) {
        logger.info("AS /user has ben called");
        logger.debug("/user called "+ count.incrementAndGet() +" times");
        logger.debug("user info: "+user.toString());
        return user;
    }

    @Configuration
    @EnableAuthorizationServer
    public static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.authenticationManager(authenticationManager);
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("resource-server")
                        .secret("resource-server-secret")
                        .authorizedGrantTypes("client_credentials")
                        .scopes("resource-server-read", "resource-server-write")
                        .authorities("ROLE_RS_READ", "ROLE_RS_WRITE");

        }
    }
}
