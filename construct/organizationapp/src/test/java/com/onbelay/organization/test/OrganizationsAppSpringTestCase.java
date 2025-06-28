package com.onbelay.organization.test;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

@WithMockUser
@ComponentScan(basePackages = {"com.onbelay.core.*", "com.onbelay.shared.*", "com.onbelay.organization.*"})
@EntityScan(basePackages = {"com.onbelay.*"})
@TestPropertySource( locations="classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrganizationsAppSpringTestCase extends OrganizationSpringTestCase {

}
