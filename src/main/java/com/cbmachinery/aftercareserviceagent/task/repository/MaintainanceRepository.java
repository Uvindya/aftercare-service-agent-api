package com.cbmachinery.aftercareserviceagent.task.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface MaintainanceRepository extends JpaRepository<Maintainance, Long> {

	@Modifying
	@Query("update Maintainance m set m.technician =:technician, m.status =:status, m.modifiedAt=:modifiedAt, m.assignedAt=:modifiedAt where m.id =:id")
	void assignTechnician(@Param("id") long id, @Param("technician") Technician technician,
			@Param("status") MaintainanceStatus status, @Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Maintainance m set m.status =:status, m.modifiedAt=:modifiedAt, m.startedAt=:modifiedAt where m.id =:id")
	void start(@Param("id") long id, @Param("status") MaintainanceStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Maintainance m set m.status =:status, m.modifiedAt=:modifiedAt, m.completedAt=:modifiedAt where m.id =:id")
	void complete(@Param("id") long id, @Param("status") MaintainanceStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Maintainance m set m.status =:status, m.modifiedAt=:modifiedAt where m.id =:id")
	void changeStatus(@Param("id") long id, @Param("status") MaintainanceStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Maintainance m set m.status =:status, m.modifiedAt=:modifiedAt, m.approvedAt=:modifiedAt where m.id =:id")
	void approve(@Param("id") long id, @Param("status") MaintainanceStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Maintainance m set m.completionNote =:completionNote, m.additionalNote =:additionalNote, m.modifiedAt=:modifiedAt where m.id =:id")
	void addNotes(@Param("id") long id, @Param("completionNote") String completionNote,
			@Param("additionalNote") String additionalNote, @Param("modifiedAt") LocalDateTime modifiedAt);

	List<Maintainance> findByProductIn(List<Product> products);

	List<Maintainance> findByReportedAtBetween(LocalDate from, LocalDate to);

	List<Maintainance> findByScheduledDateBetweenAndStatusNotIn(LocalDate from, LocalDate to,
			List<MaintainanceStatus> status);

	List<Maintainance> findByAssignedAtBetween(LocalDateTime from, LocalDateTime to);

	@Query(value = "SELECT * FROM maintainances WHERE technician_id=:technicianId AND (assigned_at >=:from OR assigned_at <=:to)", nativeQuery = true)
	List<Maintainance> findByAssignedAtBetweenForTechnician(@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to, @Param("technicianId") long technicianId);
}
