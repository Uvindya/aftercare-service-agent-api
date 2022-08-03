package com.cbmachinery.aftercareserviceagent.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
	Page<Technician> findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrErpIdContainingIgnoreCase(
			String email, String firstName, String lastName, String erpId, Pageable pageable);
}
