package com.amsidh.mvc.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final Environment environment;

	public AccountController(Environment environment) {
		this.environment = environment;
	}

	@GetMapping("/health/check")
	public ResponseEntity<String> cheakHealth() {
		return ResponseEntity
				.ok("Account web service is working on port " + environment.getProperty("local.server.port"));
	}
}
