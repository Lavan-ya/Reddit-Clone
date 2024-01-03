package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
//@CrossOrigin("http://localhost:4200/")
@RequestMapping("/api/auth/")
public class AuthController {
	private final AuthService authService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		Map<String, String> response = new HashMap<>();
		response.put("message", "User updated successfully! Check your mail to activate your account");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/verifyUser/{token}")
	public String value(@PathVariable String token) {
		String status = authService.checkUser(token);
		return status;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		log.info("login in controller");
		String value = null;
		Authentication authenticated = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticated);
		log.info("checked Authentication");
		if (authenticated.isAuthenticated()) {
			value = authService.login(loginRequest);
		}
		return value;
	}
}
