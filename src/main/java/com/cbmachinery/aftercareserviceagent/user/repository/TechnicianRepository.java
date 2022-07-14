package com.cbmachinery.aftercareserviceagent.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {

}
