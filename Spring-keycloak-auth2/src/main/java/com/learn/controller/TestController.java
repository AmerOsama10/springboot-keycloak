package com.learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/test")
@SecurityRequirement(name="Keycloak")
public class TestController {

	@GetMapping
	public String getMethod() {
		return "say Hello !!!";
	}
}
