package com.gwrocha.libary.config.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwrocha.libary.config.security.dto.TokenResponse;
import com.gwrocha.libary.models.User;

public class AuthenticationLoginFilter extends UsernamePasswordAuthenticationFilter {

	private TokenService tokenService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Authentication authentication = super.attemptAuthentication(request, response);
		
		Optional.ofNullable(authentication)
			.map(auth -> auth.getPrincipal())
			.map(principal -> (LibaryUserDetails) principal)
			.map(LibaryUserDetails::getUser)
			.map(User::getUsername)
			.map(tokenService::generateToken)
			.ifPresent(token -> writeTokenInResponse(token, response));
		
		return authentication;
	}
	
	private void writeTokenInResponse(String token, HttpServletResponse response){
		try {
			TokenResponse tokenResponse = new TokenResponse(token);
			String tokenBytes = new ObjectMapper().writeValueAsString(tokenResponse);
			response.setContentType( "application/json");
			response.getWriter().write(tokenBytes);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

}
