/*
 * Copyright (C) 2018  Atos Spain SA. All rights reserved.
 * 
 * This file is part of the phs-backend.
 * 
 * AuthTest.java is free software: you can redistribute it and/or modify it under the 
 * terms of the Apache License, Version 2.0 (the License);
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * The software is provided "AS IS", without any warranty of any kind, express or implied,
 * including but not limited to the warranties of merchantability, fitness for a particular
 * purpose and noninfringement, in no event shall the authors or copyright holders be 
 * liable for any claim, damages or other liability, whether in action of contract, tort or
 * otherwise, arising from, out of or in connection with the software or the use or other
 * dealings in the software.
 * 
 * See README file for the full disclaimer information and LICENSE file for full license 
 * information in the project root.
 * 
 * @author	Carlos Cavero Barca
 *			Atos Research and Innovation, Atos SPAIN SA
 * 
 * Spring boot application test empty for KeyCloak auth
 */

package net.atos.ari.auth;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.representations.AccessTokenResponse;
import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import net.atos.ari.auth.exception.NotAuthorizedException;
import net.atos.ari.auth.model.KeycloakUser;
import net.atos.ari.auth.service.AuthService;

@RunWith(SpringRunner.class)
// @SpringBootTest
@TestPropertySource(properties = { "keycloak.url=http://localhost:8080/auth", 
		"keycloak.realm=test", "keycloak.client_id=test"})
@Profile("test")
public class AuthServiceTest {

	@Test
	public void contextLoads() {
	}
	
    /**
     * Create the Bean Auth Service.
     * 
     * @return the Auth Service
     */
	@TestConfiguration
	static class AuthServiceImplTestContextConfiguration {
	  
		@Bean
		public AuthService authService() {
			return new AuthService();
		}
	}
	 
	@Autowired
	private AuthService authService;
	
	@Mock
	private RestTemplate restTemplate;
	
	private String authToken = "123456789";
	
    /**
     * SetUp the system to mock the services.
     * 
     * @return void
     */
	@Before
	public void setUp() {
		
	    // Mock any exchange REST call to provide status ok
	    when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
             .thenReturn(new ResponseEntity<String>(HttpStatus.OK));
		
		authService.setRestTemplate(restTemplate);
	}
	
    /**
     * Test all the Service functionalities provided.
     * 
     * @return void
     * @throws NotAuthorizedException, when some not authorization raises 
     */
	@Test
	public void callService_returnExpectedBehaviourTest() throws NotAuthorizedException {
		KeycloakUser user = KeycloakUser.builder()
				.username("test")
				.password("test")
				.build();
				
	    // AccessTokenResponse found = authService.login(user);
	  
	    // assertThat(found.getIdToken(), authToken);
		// assertThat(token, Matchers.<String>is("123456"));
	    
	    authService.user(authToken);
	 }

}