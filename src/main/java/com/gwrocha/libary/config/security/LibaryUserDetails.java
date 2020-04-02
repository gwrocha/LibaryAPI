package com.gwrocha.libary.config.security;

import static com.gwrocha.libary.models.enums.StatusUser.ACCOUNT_EXPIRED;
import static com.gwrocha.libary.models.enums.StatusUser.ACCOUNT_LOCKED;
import static com.gwrocha.libary.models.enums.StatusUser.ACTIVE;
import static com.gwrocha.libary.models.enums.StatusUser.CREDENTIALS_EXPIRED;
import static java.util.stream.Collectors.toList;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gwrocha.libary.models.User;

public class LibaryUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User user;
	
	public LibaryUserDetails(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
					.map(role -> "ROLE_" + role.name())
					.map(this::toGrantedAuthority)
					.collect(toList());
	}
	
	private GrantedAuthority toGrantedAuthority(String role) {
		return () -> role;
	}

	@Override
	public String getPassword() {
		return getUser().getPassword();
	}

	@Override
	public String getUsername() {
		return getUser().getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !getUser().getStatus().equals(ACCOUNT_EXPIRED);
	}

	@Override
	public boolean isAccountNonLocked() {
		return !getUser().getStatus().equals(ACCOUNT_LOCKED);
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !getUser().getStatus().equals(CREDENTIALS_EXPIRED);
	}

	@Override
	public boolean isEnabled() {
		return getUser().getStatus().equals(ACTIVE);
	}

}
