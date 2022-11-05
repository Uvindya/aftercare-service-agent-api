package com.cbmachinery.aftercareserviceagent.task.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public interface BreakdownRepository extends JpaRepository<Breakdown, Long> {

	@Modifying
	@Query("update Breakdown m set m.technician =:technician, m.status =:status, m.modifiedAt=:modifiedAt, m.assignedAt=:modifiedAt where m.id =:id")
	void assignTechnician(@Param("id") long id, @Param("technician") Technician technician,
			@Param("status") BreakdownStatus status, @Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Breakdown m set m.status =:status, m.modifiedAt=:modifiedAt, m.startedAt=:modifiedAt where m.id =:id")
	void start(@Param("id") long id, @Param("status") BreakdownStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Breakdown m set m.status =:status, m.modifiedAt=:modifiedAt, m.completedAt=:modifiedAt where m.id =:id")
	void complete(@Param("id") long id, @Param("status") BreakdownStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Breakdown m set m.status =:status, m.modifiedAt=:modifiedAt where m.id =:id")
	void changeStatus(@Param("id") long id, @Param("status") BreakdownStatus status,
			@Param("modifiedAt") LocalDateTime modifiedAt);

	@Modifying
	@Query("update Breakdown m set m.completionNote =:completionNote, m.additionalNote =:additionalNote, m.solution =:solution, m.rootCause =:rootCause, m.modifiedAt=:modifiedAt where m.id =:id")
	void addNotes(@Param("id") long id, @Param("completionNote") String completionNote,
			@Param("additionalNote") String additionalNote, @Param("rootCause") String rootCause,
			@Param("solution") String solution, @Param("modifiedAt") LocalDateTime modifiedAt);

	List<Breakdown> findByProductIn(List<Product> products);

	List<Breakdown> findByReportedAtBetween(LocalDate from, LocalDate to);

	List<Breakdown> findByAssignedAtBetween(LocalDateTime from, LocalDateTime to);

	@Query(value = "SELECT * FROM breakdowns WHERE technician_id=:technicianId AND (assigned_at >=:from OR assigned_at <=:to)", nativeQuery = true)
	List<Breakdown> findByAssignedAtBetweenForTechnician(@Param("from") LocalDateTime from,
			@Param("to") LocalDateTime to, @Param("technicianId") long technicianId);

	long countByStatusIn(List<BreakdownStatus> status);
}
