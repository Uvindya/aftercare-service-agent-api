package com.cbmachinery.aftercareserviceagent.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownNotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;

public interface BreakdownService {
	BasicBreakdownOutputDTO save(BreakdownInputDTO breakdownInput);

	Page<BasicBreakdownOutputDTO> findAll(Pageable pageable, String searchTerm);

	BreakdownOutputDTO findById(long id);

	BreakdownOutputDTO assignTechnician(TechnicianTaskAssignmentDTO technicianTaskAssignment);

	List<BasicBreakdownOutputDTO> findMyAssigns(String username);

	List<BasicBreakdownOutputDTO> findMyOwnership(String username);

	BreakdownOutputDTO changeStatus(long id, BreakdownStatus status);

	BreakdownOutputDTO start(long id, BreakdownStatus status);

	BreakdownOutputDTO complete(long id, BreakdownStatus status);

	BreakdownOutputDTO addNotes(long id, BreakdownNotesInputDTO notesInput);

	List<Breakdown> findByReportedAt(LocalDate from, LocalDate to);

	List<Breakdown> findByAssignedAt(LocalDateTime from, LocalDateTime to);

	List<Breakdown> findByAssignedAtForTechnician(LocalDateTime from, LocalDateTime to, long technicianId);

	long count();

	long inProgressCount();

	long pendingCount();

	long pendingAcceptenceCount();

	long notStartedCount();

	long completedCount();

	void importFromCSV(MultipartFile csv);
}
