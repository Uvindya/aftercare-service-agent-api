package com.cbmachinery.aftercareserviceagent.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.user.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

}
