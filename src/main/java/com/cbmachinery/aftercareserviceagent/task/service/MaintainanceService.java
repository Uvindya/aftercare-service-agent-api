package com.cbmachinery.aftercareserviceagent.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceReScheduleInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.NotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;

public interface MaintainanceService {
	BasicMaintainanceOutputDTO save(MaintainanceInputDTO maintainanceInput);

	Page<BasicMaintainanceOutputDTO> findAll(Pageable pageable, String searchTerm);

	MaintainanceOutputDTO findById(long id);

	MaintainanceOutputDTO assignTechnician(TechnicianTaskAssignmentDTO technicianTaskAssignment);

	List<BasicMaintainanceOutputDTO> findMyAssigns(String username);

	List<BasicMaintainanceOutputDTO> findMyOwnership(String username);

	MaintainanceOutputDTO start(long id);

	MaintainanceOutputDTO complete(long id);

	MaintainanceOutputDTO approve(long id);

	MaintainanceOutputDTO accept(long id);

	MaintainanceOutputDTO skip(long id);

	MaintainanceOutputDTO reSchedule(long id, MaintainanceReScheduleInputDTO reScheduleInput);

	MaintainanceOutputDTO addNotes(long id, NotesInputDTO notesInput);

	List<Maintainance> findByReportedAt(LocalDate from, LocalDate to);

	List<Maintainance> findByScheduledAt(LocalDate from, LocalDate to);

	List<Maintainance> findUpcomming(LocalDate from, LocalDate to);

	List<Maintainance> findByAssignedAt(LocalDateTime from, LocalDateTime to);

	List<Maintainance> findByAssignedAtForTechnician(LocalDateTime from, LocalDateTime to, long technicianId);

	long count();

	long inProgressCount();

	long notStartedCount();

	long completedCount();

	long scheduledCount();

	void importFromCSV(MultipartFile csv);
}
