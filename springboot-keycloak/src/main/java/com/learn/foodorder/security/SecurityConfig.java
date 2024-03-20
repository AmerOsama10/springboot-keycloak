package com.learn.foodorder.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	//our authentication converter that we will configure it instead of the default one
	@Autowired
	JwtAuthConverter jwtAuthConverter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(t-> t.disable());
		http.authorizeHttpRequests(authorize -> {
			authorize
			.requestMatchers(HttpMethod.GET, "/restaurant/public/list").permitAll()
			.requestMatchers(HttpMethod.GET, "/restaurant/public/menu/*").permitAll()
			.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
			.anyRequest().authenticated();	
		});
		
		http.oauth2ResourceServer(t-> {
			//JWT do not call the keycloak it vertify the token by itself (he only need expired and issuer)
			//to use the default jwt authentication converter
			t.jwt(Customizer.withDefaults());

			// to use our own jwt authentication converter
			//t.jwt(configure-> configure.jwtAuthenticationConverter(jwtAuthConverter));
			
			//Opaque call keycloak throw rest 
			//it needs some meta data for keycloak to call
			//to use the default opaque
		    //t.opaqueToken(Customizer.withDefaults());
	});
	http.sessionManagement(
		t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	);		
		
		return http.build();
	}

//  By default, Spring Security uses the "ROLE_" prefix for role-based expressions.
//	However, by setting it to an empty string, you're effectively removing the prefix. 	
	@Bean
	public DefaultMethodSecurityExpressionHandler msecurity() {
	  DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler = 
	      new DefaultMethodSecurityExpressionHandler();
	  defaultMethodSecurityExpressionHandler.setDefaultRolePrefix("");
	  return defaultMethodSecurityExpressionHandler;
	}
	
	@Bean
	public JwtAuthenticationConverter con() {
	  JwtAuthenticationConverter c =new JwtAuthenticationConverter();
	  JwtGrantedAuthoritiesConverter cv = new JwtGrantedAuthoritiesConverter();
	  cv.setAuthorityPrefix(""); // Default "SCOPE_"
      cv.setAuthoritiesClaimName("roles"); // Default "scope" or "scp"
	  c.setJwtGrantedAuthoritiesConverter(cv);
	  return c;
	}

}