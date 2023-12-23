package com.example.demo.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.model.User;
import com.example.demo.model.VerificationToken;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final JWTService jwtService;
	PasswordEncoder passwordEncoder;
	JavaMailSender javaMailSender;
	private AuthenticationManager authenticationManager;

	@Transactional
	public void signup(RegisterRequest registerRequest) {

		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setEmail(registerRequest.getEmail());
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
		String token = generateVerificationToken(user);
		sendVerificationToken("lavanyaselvaraj38@gmail.com", token, user);

	}

	private void sendVerificationToken(String mail, String token, User user) {
		String link = "http://localhost:8080/api/auth/verifyUser/" + token;
		String emailBody = "Click here to verify your account : " + link;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail);
		mailMessage.setSubject("User:" + " " + user.getUsername() + " " + "Activation email");
		mailMessage.setText(emailBody);
		javaMailSender.send(mailMessage);
	}

	private String generateVerificationToken(User user) {
		String token = String.valueOf(UUID.randomUUID());
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationToken.setExpiryDate(Instant.now().plusSeconds(24 * 60 * 60));
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public String checkUser(String token) {
		VerificationToken userDetails = verificationTokenRepository.findByToken(token);
		User user = userDetails.getUser();
		if (user != null) {
			user.setEnabled(true);
			userRepository.save(user);
			return "User activated and enabled Successfully";
		}
		return "Invalid Token";
	}

	public String login(LoginRequest loginRequest) {
		log.info("Entered authservice");
		String token = jwtService.generateToken(loginRequest.getEmail());
		return token;
	}
}
