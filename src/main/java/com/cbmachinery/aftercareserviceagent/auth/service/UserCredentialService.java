package com.cbmachinery.aftercareserviceagent.auth.service;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;

public interface UserCredentialService {
	UserCredential findByUsername(String username);

	UserCredential save(UserCredentialInputDTO userCredentialInputDTO);

	UserCredential changePassword(long id, String oldPassword, String newPassword);

	UserCredential findById(long id);

	void changeActiveStatus(String username, boolean active);
}
