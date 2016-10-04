This example is based on the following resources:

 - http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/
 - http://projects.spring.io/spring-security-oauth/docs/oauth2.html
 - https://github.com/spring-guides/tut-spring-boot-oauth2/
 - https://github.com/spring-guides/tut-spring-security-and-angular-js/tree/master/oauth2
 - https://spring.io/blog/2015/11/30/migrating-oauth2-apps-from-spring-boot-1-2-to-1-3



How to test:

1. $ `cd authorization-server;mvn spring-boot:run`
2. $ `cd resource-server;mvn spring-boot:run`

3. Obtain token with: $ `curl resource-server:resource-server-secret@localhost:8080/oauth/token -d grant_type=client_credentials` and save it in TOKEN=.......
4. Check the user endpoint with: $ `curl -H "Authorization: Bearer $TOKEN" -v localhost:8080/user`
5. Access the resource with: $ `curl -H "Authorization: Bearer $TOKEN" -v localhost:9090`
6. Update the resource with: $ `curl -H "Content-Type: application/json" -H "Authorization: Bearer $TOKEN" -X POST -d "Bonjour" -v localhost:9090`


For generating your own key (as written in the stytex.de blog):

`
keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=jwt, L=Lugano, S=Lugano, C=CH" -keypass mySecretKey -keystore jwt.jks -storepass mySecretKey
`

and copy the pubkey part:

`
keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
`

and put it in resource-server/src/main/resources.public.cert
copy jwt.jks in authorization-server/src/main/resources/jwk.jks