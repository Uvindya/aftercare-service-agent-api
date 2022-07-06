package com.cbmachinery.aftercareserviceagent.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.repository.UserCredentialRepository;
import com.cbmachinery.aftercareserviceagent.service.UserCredentialService;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

	private final UserCredentialRepository userCredentialRepository;

	public UserCredentialServiceImpl(final UserCredentialRepository userCredentialRepository) {
		super();
		this.userCredentialRepository = userCredentialRepository;
	}

	@Override
	public UserCredential findByUsername(String username) {
		return this.userCredentialRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username : " + username));
	}

	@Override
	public UserCredential findById(long id) {
		return this.userCredentialRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
	}

	@Override
	public UserCredential save(UserCredential userCredential) {
		return this.userCredentialRepository.save(userCredential);
	}

}
