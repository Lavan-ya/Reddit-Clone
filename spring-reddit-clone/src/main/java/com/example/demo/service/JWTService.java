package com.example.demo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTService {

	private final CustomUserDetailsService customUserDetailsService;

	public String generateToken(String email) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, email);
	}

	private String createToken(Map<String, Object> claims, String email) {

		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 6))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keys = Decoders.BASE64.decode(
				"secretkeylavanyahellohey6jgj4g324jqrbj32v5j3v4b32mnvnvdfvfdsvdsb866vdsfsdfbfds6876bsdfb86b6fd8b");
		return Keys.hmacShaKeyFor(keys);
	}

	public String extractUserFromToken(String token) {
		Claims claim = getUser(token);
		if (isTokenValid(token)) {
			String userName = claim.getSubject();
			return userName;
		} else {
			return "Token expired";
		}
	}

	public boolean isTokenValid(String token) {
		Claims claim = getUser(token);
		if (claim.getExpiration().after(new Date())) {
			return true;
		}
		return false;
	}

	private Claims getUser(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
}
