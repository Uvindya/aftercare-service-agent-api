package com.cbmachinery.aftercareserviceagent.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmachinery.aftercareserviceagent.auth.util.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserCredentialService userCredentialService;

	public CustomUserDetailsService(final UserCredentialService userCredentialService) {
		super();
		this.userCredentialService = userCredentialService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		return UserPrincipal.create(this.userCredentialService.findByUsername(username));
	}

	@Transactional
	public UserDetails loadUserCredentialsById(long id) {
		return UserPrincipal.create(this.userCredentialService.findById(id));
	}

}
