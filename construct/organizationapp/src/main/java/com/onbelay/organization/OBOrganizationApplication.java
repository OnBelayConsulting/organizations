/*
 * Copyright (c) 2018-2019 OnBelay Consulting
 * All Rights Reserved
 */

package com.onbelay.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@ComponentScan(basePackages = {"com.onbelay.core.*", "com.onbelay.organization.*", "com.onbelay.organization.*"})
@EntityScan("com.onbelay.*")
@SpringBootApplication
public class OBOrganizationApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(OBOrganizationApplication.class, args);
		
		
	}

	
}
