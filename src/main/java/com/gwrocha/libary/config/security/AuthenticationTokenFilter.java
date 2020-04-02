package com.gwrocha.libary.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gwrocha.libary.repositories.UserRepository;

public class AuthenticationTokenFilter extends OncePerRequestFilter{

	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer ";
	
	private TokenService tokenService;
	
	private UserRepository userRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authtorization = request.getHeader(HEADER_AUTHORIZATION);
		
		Optional.ofNullable(authtorization)
				.filter(auth -> !auth.isEmpty())
				.filter(auth -> auth.startsWith(TOKEN_PREFIX))
				.map(auth -> auth.replace(TOKEN_PREFIX, ""))
				.map(tokenService::extractUsernameFrom)
				.map(userRepo::findByUsername)
				.map(LibaryUserDetails::new)
				.map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities()))
				.ifPresent(SecurityContextHolder.getContext()::setAuthentication);
		
		doFilter(request, response, filterChain);
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public UserRepository getUserRepository() {
		return userRepo;
	}

	public void setUserRepository(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

}
