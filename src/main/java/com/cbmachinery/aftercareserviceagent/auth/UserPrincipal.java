package com.cbmachinery.aftercareserviceagent.auth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cbmachinery.aftercareserviceagent.model.UserCredential;
import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
public class UserPrincipal implements UserDetails {

	private long id;

	private String username;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private boolean active;

	public UserPrincipal(long id, String username, String password, boolean active,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.active = active;
	}

	public static UserPrincipal create(UserCredential userCredential) {
		List<GrantedAuthority> authorities = new LinkedList<>();
		authorities.add(new SimpleGrantedAuthority(userCredential.getRole().name()));

		return new UserPrincipal(userCredential.getId(), userCredential.getUsername(), userCredential.getPassword(),
				userCredential.isActive(), authorities);
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active;
	}

	@Override
	public boolean isAccountNonLocked() {
		return active;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return active;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}
}
