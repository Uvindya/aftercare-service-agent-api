package com.cbmachinery.aftercareserviceagent.auth.service.impl;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.repository.UserCredentialRepository;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;
import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;

@Service
public class UserCredentialServiceImpl implements UserCredentialService {

	private final UserCredentialRepository userCredentialRepository;
	private final PasswordEncoder passwordEncoder;

	public UserCredentialServiceImpl(final UserCredentialRepository userCredentialRepository,
			@Lazy final PasswordEncoder passwordEncoder) {
		super();
		this.userCredentialRepository = userCredentialRepository;
		this.passwordEncoder = passwordEncoder;
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
	public UserCredential save(UserCredentialInputDTO userCredentialInputDTO) {
		if (this.userCredentialRepository.findByUsername(userCredentialInputDTO.getUsername()).isPresent()) {
			throw new IllegalArgumentException("User Already Exists");
		}
		return this.userCredentialRepository.save(UserCredential.builder().active(true)
				.password(passwordEncoder.encode(userCredentialInputDTO.getPassword()))
				.role(userCredentialInputDTO.getRole()).username(userCredentialInputDTO.getUsername()).build());
	}

	@Override
	public UserCredential changePassword(long id, String oldPassword, String newPassword) {
		UserCredential userCredential = this.findById(id);

		if (!this.passwordEncoder.matches(oldPassword, userCredential.getPassword())) {
			throw new IllegalArgumentException("Invalid current password provided");
		}
		return this.userCredentialRepository.save(UserCredential.builder().id(userCredential.getId())
				.username(userCredential.getUsername()).password(passwordEncoder.encode(newPassword))
				.role(userCredential.getRole()).active(userCredential.isActive())
				.createdAt(userCredential.getCreatedAt()).createdBy(userCredential.getCreatedBy()).build());
	}

	@Transactional
	@Override
	public void changeActiveStatus(String username, boolean active) {
		this.userCredentialRepository.changeActiveStatus(username, active);
	}

}
