package com.cbmachinery.aftercareserviceagent.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmachinery.aftercareserviceagent.service.UserCredentialService;

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
