package com.cbmachinery.aftercareserviceagent.auth.service;

import com.cbmachinery.aftercareserviceagent.auth.dto.ChangeCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.LoginInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.LoginOutputDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.PlainResponse;
import com.cbmachinery.aftercareserviceagent.auth.dto.StatusDTO;
import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialOutputDTO;

public interface AuthService {
	LoginOutputDTO authenticate(LoginInputDTO loginInputDto);

	PlainResponse changePassword(long credentialId, ChangeCredentialInputDTO changeCredentialInput);

	StatusDTO checkUsernameStatus(String username);

	UserCredentialOutputDTO getBasicUserProfile(String username);

}
