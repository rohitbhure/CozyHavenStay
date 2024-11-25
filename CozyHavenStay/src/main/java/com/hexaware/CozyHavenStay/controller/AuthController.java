package com.hexaware.CozyHavenStay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.CozyHavenStay.dto.JWTAuthResponse;
import com.hexaware.CozyHavenStay.dto.LoginDto;
import com.hexaware.CozyHavenStay.dto.RegisterDto;
import com.hexaware.CozyHavenStay.service.AuthService;

@RestController

@RequestMapping("/api/authenticate")

//@CrossOrigin("http://localhost:3000")

@CrossOrigin("*")

public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {

		this.authService = authService;

	}

	@PostMapping(value = { "/login", "/signin" })

	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto dto)

	{

		JWTAuthResponse token = authService.login(dto);

		/*
		 * JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		 * 
		 * jwtAuthResponse.setAccessToken(token);
		 */

		return ResponseEntity.ok(token);

	}

	@PostMapping(value = { "/register", "/signup" })

	public ResponseEntity<String> register(@RequestBody RegisterDto dto)

	{

		String value = authService.register(dto);

		return new ResponseEntity<>(value, HttpStatus.CREATED);

	}

}

