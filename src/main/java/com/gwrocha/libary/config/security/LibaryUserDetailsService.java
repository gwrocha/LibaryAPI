package com.gwrocha.libary.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gwrocha.libary.models.User;
import com.gwrocha.libary.repositories.UserRepository;

@Service
public class LibaryUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		return Optional.ofNullable(user)
					.map(LibaryUserDetails::new)
					.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' + not found."));
	}

}
