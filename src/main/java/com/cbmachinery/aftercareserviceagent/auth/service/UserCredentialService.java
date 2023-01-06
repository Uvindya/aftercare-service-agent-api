package com.cbmachinery.aftercareserviceagent.auth.service;

import java.util.List;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;

public interface UserCredentialService {
	UserCredential findByUsername(String username);

	UserCredential save(UserCredentialInputDTO userCredentialInputDTO);

	UserCredential changePassword(long id, String oldPassword, String newPassword);

	UserCredential findById(long id);

	void changeActiveStatus(String username, boolean active);

	boolean usernamesExists(List<String> usernames);

	List<UserCredential> findByRole(Role role);

	UserCredential resetPassword(long id, String newPassword);
}
