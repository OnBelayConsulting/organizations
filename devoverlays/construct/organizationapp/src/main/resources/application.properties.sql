spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=oborganizations;trustServerCertificate=true
spring.datasource.username=organizations
spring.datasource.password=organizations
spring.jpa.open-in-view=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServer2012Dialect

server.port=9100
server.servlet.context-path=/Organizations
server.shutdown=graceful
spring.application.name=Organizations
spring.main.allow-bean-definition-overriding=true
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
spring.profiles.active=messaging
spring.cloud.stream.bindings.out.destination=org.organizations.save
spring.cloud.stream.bindings.out.contentType=application/json

endpoints.health.enabled=true
endpoints.metrics.enabled=true
logging.level.org.springframework=DEBUG



spring.security.oauth2.client.provider.okta.issuer-uri=http://localhost:8383/realms/master
spring.security.oauth2.client.provider.okta.token-uri=http://localhost:8383/realms/master/protocol/openid-connect/token
spring.security.oauth2.client.registration.okta.client-id=organizations
spring.security.oauth2.client.registration.okta.client-secret=6018CaG1qcg6VWtfikxEq0VVFjyKtriO
spring.security.oauth2.client.registration.okta.scope=openid, profile, email
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost:8383/realms/master/protocol/openid-connect/certs
