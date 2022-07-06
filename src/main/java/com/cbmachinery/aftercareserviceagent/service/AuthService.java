package com.cbmachinery.aftercareserviceagent.service;

import com.cbmachinery.aftercareserviceagent.dto.ChangeCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginInputDTO;
import com.cbmachinery.aftercareserviceagent.dto.LoginOutputDTO;
import com.cbmachinery.aftercareserviceagent.dto.PlainResponse;
import com.cbmachinery.aftercareserviceagent.dto.StatusDTO;
import com.cbmachinery.aftercareserviceagent.dto.UserCredentialOutputDTO;

public interface AuthService {
	LoginOutputDTO authenticate(LoginInputDTO loginInputDto);

	PlainResponse changePassword(long credentialId, ChangeCredentialInputDTO changeCredentialInput);

	StatusDTO checkUsernameStatus(String username);

	UserCredentialOutputDTO getBasicUserProfile(String username);

}
