package com.cbmachinery.aftercareserviceagent.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface MaintainanceRepository extends JpaRepository<Maintainance, Long> {

	@Modifying
	@Query("update Maintainance m set m.technician =:technician where m.id =:id")
	void assignTechnician(@Param("id") long id, @Param("technician") Technician technician);
}
