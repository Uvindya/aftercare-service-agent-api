package com.cbmachinery.aftercareserviceagent.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;

public interface MaintainanceRepository extends JpaRepository<Maintainance, Long> {

}
