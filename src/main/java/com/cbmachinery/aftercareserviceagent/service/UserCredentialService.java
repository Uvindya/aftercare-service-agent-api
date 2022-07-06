package com.cbmachinery.aftercareserviceagent.service;

import com.cbmachinery.aftercareserviceagent.model.UserCredential;

public interface UserCredentialService {
	UserCredential findByUsername(String username);

	UserCredential save(UserCredential userCredential);

	UserCredential findById(long id);
}
