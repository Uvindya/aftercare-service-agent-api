package com.cbmachinery.aftercareserviceagent.auth.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.auth.dto.ChangeCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.LoginInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.LoginOutputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.PlainResponse;
import com.cbmachinery.aftercareserviceagent.auth.dto.StatusDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialOutputDTO;
import com.cbmachinery.aftercareserviceagent.auth.jwt.JwtTokenProvider;
import com.cbmachinery.aftercareserviceagent.auth.service.AuthService;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserCredentialService userCredentialService;
	private final JwtTokenProvider tokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserCredentialService userCredentialService,
			JwtTokenProvider tokenProvider) {
		super();
		this.authenticationManager = authenticationManager;
		this.userCredentialService = userCredentialService;
		this.tokenProvider = tokenProvider;
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
		this.userCredentialService.changePassword(credentialId, changeCredentialInput.getCurrentPassword(),
				changeCredentialInput.getNewPassword());
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
