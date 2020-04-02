package com.gwrocha.libary.config.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.gwrocha.libary.repositories.UserRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private LibaryUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private TokenService tokenService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.cors().disable()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest().authenticated().and() 
			.addFilter(authenticationLoginFilter())
			.addFilterAfter(authenticationTokenFilter(), AuthenticationLoginFilter.class)
			.sessionManagement().sessionCreationPolicy(STATELESS)			;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	private AuthenticationLoginFilter authenticationLoginFilter() throws Exception {
		AuthenticationLoginFilter loginFilter = new AuthenticationLoginFilter();
		loginFilter.setTokenService(tokenService);
		loginFilter.setAuthenticationManager(authenticationManager());
		return loginFilter;
	}
	
	private AuthenticationTokenFilter authenticationTokenFilter() {
		AuthenticationTokenFilter tokenFilter = new AuthenticationTokenFilter();
		tokenFilter.setTokenService(tokenService);
		tokenFilter.setUserRepository(userRepo);
		return tokenFilter;
	}
	
}
