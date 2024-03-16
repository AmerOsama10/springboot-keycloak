package com.learn.foodorder.security;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	// 1. we need to extract all the authorities that is realmAccess
	// 2. convert values of the map to list of strings by ObjectMapper and we will have list of roles of string
	// 3. make a list of GrantedAuthority and add all the roles on it
	// 4. make new JwtAuthenticationToken for these roles
	@Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> roles = extractAuthorities(jwt);
        System.err.println(new JwtAuthenticationToken(jwt, roles).toString());
        return new JwtAuthenticationToken(jwt, roles);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        if(jwt.getClaim("realm_access") != null) {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            Object mapRoleValues = realmAccess.get("roles");
            
            System.err.println("realm Access Map "+realmAccess);
            ObjectMapper mapper = new ObjectMapper();
            
            List<String> keycloakRoles = mapper.convertValue(mapRoleValues, new TypeReference<List<String>>(){});
            System.err.println("keycloak Roles list "+keycloakRoles);

            List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

            for (String keycloakRole : keycloakRoles) {
            	//roles.add(new SimpleGrantedAuthority("ROLE_"+keycloakRole)); //when we use the default prefix
            	roles.add(new SimpleGrantedAuthority(keycloakRole)); //when we use the bean empty prefix

            }
            System.err.println(roles);
            return roles;
        }
        return new ArrayList<GrantedAuthority>();
    }
}