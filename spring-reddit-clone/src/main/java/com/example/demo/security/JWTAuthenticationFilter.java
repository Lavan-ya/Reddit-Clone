package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		String userName = null;
		String header = request.getHeader("Authorization");
		try {
			if (header != null && header.startsWith("Bearer ")) {
				token = header.substring(7);
				userName = jwtService.extractUserFromToken(token);
			}
			UserDetails user = customUserDetailsService.loadUserByUsername(userName);
			if (userName.equals(user.getUsername())) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, null,
						null);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} catch (UsernameNotFoundException e) {
			log.error("Error processing JWT token: {} catch exception.....", e.getMessage(), e);
		}

		filterChain.doFilter(request, response);
	}
}
