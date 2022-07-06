package com.cbmachinery.aftercareserviceagent.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.auth.JwtTokenProvider;
import com.cbmachinery.aftercareserviceagent.dto.ChangeCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginOutputDTO;
import com.cbmachinery.aftercareserviceagent.dto.PlainResponse;
import com.cbmachinery.aftercareserviceagent.dto.StatusDTO;
import com.cbmachinery.aftercareserviceagent.dto.UserCredentialOutputDTO;
import com.cbmachinery.aftercareserviceagent.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.service.AuthService;
import com.cbmachinery.aftercareserviceagent.service.UserCredentialService;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserCredentialService userCredentialService;
	private final JwtTokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserCredentialService userCredentialService,
			JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.userCredentialService = userCredentialService;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public LoginOutputDTO authenticate(LoginInputDTO loginInputDto) {
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginInputDto.getUsername(), loginInputDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new LoginOutputDTO(this.tokenProvider.generateToken(authentication));
	}

	@Override
	public PlainResponse changePassword(long credentialId, ChangeCredentialInputDTO changeCredentialInput) {
		UserCredential userCredential = this.userCredentialService.findById(credentialId);

		if (!this.passwordEncoder.matches(changeCredentialInput.getCurrentPassword(), userCredential.getPassword())) {
			throw new IllegalArgumentException("Invalid current password provided");
		}
		this.userCredentialService.save(UserCredential.builder().id(userCredential.getId())
				.username(userCredential.getUsername())
				.password(passwordEncoder.encode(changeCredentialInput.getNewPassword())).role(userCredential.getRole())
				.active(userCredential.isActive()).createdAt(userCredential.getCreatedAt()).build());
		return new PlainResponse("Password successfully changed !!!");
	}

	@Override
	public StatusDTO checkUsernameStatus(String username) {
		try {
			this.userCredentialService.findByUsername(username);
			return new StatusDTO(true);
		} catch (UsernameNotFoundException e) {
			return new StatusDTO(false);
		}
	}

	@Override
	public UserCredentialOutputDTO getBasicUserProfile(String username) {
		return this.userCredentialService.findByUsername(username).viewAsOutput();
	}

}
