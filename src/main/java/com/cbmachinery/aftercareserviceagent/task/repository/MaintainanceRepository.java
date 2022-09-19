package com.cbmachinery.aftercareserviceagent.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface MaintainanceRepository extends JpaRepository<Maintainance, Long> {

	@Modifying
	@Query("update Maintainance m set m.technician =:technician, m.status =:status where m.id =:id")
	void assignTechnician(@Param("id") long id, @Param("technician") Technician technician,
			@Param("status") MaintainanceStatus status);

	@Modifying
	@Query("update Maintainance m set m.status =:status where m.id =:id")
	void changeStatus(@Param("id") long id, @Param("status") MaintainanceStatus status);

	@Modifying
	@Query("update Maintainance m set m.completionNote =:completionNote, m.additionalNote =:additionalNote where m.id =:id")
	void addNotes(@Param("id") long id, @Param("completionNote") String completionNote,
			@Param("additionalNote") String additionalNote);
}
