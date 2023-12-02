package com.example.demo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController {
	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		String token = authService.signup(registerRequest);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		return new ResponseEntity<>("User updated successfully", headers, HttpStatus.OK);
	}

	@GetMapping("/getValue")
	public String value() {
		return "hello welcome working";
	}

}
