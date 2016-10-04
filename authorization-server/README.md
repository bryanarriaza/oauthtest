Based from http://projects.spring.io/spring-security-oauth/docs/oauth2.html

Running the authorization server:

$ `mvn spring-boot:run`

Obtain token:

$ `curl resource-server:resource-server-secret@localhost:8080/oauth/token -d grant_type=client_credentials`