package com.learn.foodorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(
	    name = "Keycloak"
	    , openIdConnectUrl = "http://localhost:8081/realms/local-dev/.well-known/openid-configuration"
	    , scheme = "bearer"
	    , type = SecuritySchemeType.OPENIDCONNECT
	    , in = SecuritySchemeIn.HEADER
	    )
public class SpringKeycloakApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringKeycloakApplication.class, args);
	}

}
