package com.cbmachinery.aftercareserviceagent.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

	Optional<UserCredential> findByUsername(String username);

	List<UserCredential> findByUsernameIn(List<String> usernames);

	List<UserCredential> findByRole(Role role);

	boolean existsByUsername(String username);

	@Modifying
	@Query("update UserCredential u set u.active =:active where u.username =:username")
	void changeActiveStatus(@Param("username") String username, @Param("active") boolean active);
}
