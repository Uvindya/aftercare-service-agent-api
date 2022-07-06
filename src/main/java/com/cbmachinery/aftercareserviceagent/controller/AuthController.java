package com.cbmachinery.aftercareserviceagent.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.dto.ChangeCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginOutputDTO;
import com.cbmachinery.aftercareserviceagent.dto.PlainResponse;
import com.cbmachinery.aftercareserviceagent.dto.StatusDTO;
import com.cbmachinery.aftercareserviceagent.dto.UserCredentialOutputDTO;
import com.cbmachinery.aftercareserviceagent.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(final AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/signin")
	public ResponseEntity<LoginOutputDTO> authenticate(@Valid @RequestBody LoginInputDTO loginInputDto) {
		return ResponseEntity.ok(this.authService.authenticate(loginInputDto));
	}

	@PutMapping("/change/pw/{cId}")
	public ResponseEntity<PlainResponse> changePassword(@PathVariable long cId,
			@Valid @RequestBody ChangeCredentialInputDTO changeCredentialInput) {
		return ResponseEntity.ok(this.authService.changePassword(cId, changeCredentialInput));
	}

	@GetMapping("/status/username/{username}")
	public ResponseEntity<StatusDTO> checkUsernameStatus(@PathVariable String username) {
		return ResponseEntity.ok(this.authService.checkUsernameStatus(username));
	}

	@PostMapping("/me")
	public ResponseEntity<UserCredentialOutputDTO> getProfile(Principal principal) {
		return ResponseEntity.ok(this.authService.getBasicUserProfile(principal.getName()));
	}
}
