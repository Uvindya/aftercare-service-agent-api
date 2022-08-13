package com.cbmachinery.aftercareserviceagent.user.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cbmachinery.aftercareserviceagent.user.model.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
	Page<Client> findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrErpIdContainingIgnoreCase(
			String email, String firstName, String lastName, String erpId, Pageable pageable);

	Optional<Client> findByEmail(String username);
}
