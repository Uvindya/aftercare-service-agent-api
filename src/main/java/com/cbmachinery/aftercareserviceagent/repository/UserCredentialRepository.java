package com.cbmachinery.aftercareserviceagent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.model.UserCredential;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

	Optional<UserCredential> findByUsername(String username);

	boolean existsByUsername(String username);
}
