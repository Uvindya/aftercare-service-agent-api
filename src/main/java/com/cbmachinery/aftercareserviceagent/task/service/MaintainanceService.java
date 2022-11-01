package com.cbmachinery.aftercareserviceagent.task.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.NotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;

public interface MaintainanceService {
	BasicMaintainanceOutputDTO save(MaintainanceInputDTO maintainanceInput);

	Page<BasicMaintainanceOutputDTO> findAll(Pageable pageable, String searchTerm);

	MaintainanceOutputDTO findById(long id);

	MaintainanceOutputDTO assignTechnician(TechnicianTaskAssignmentDTO technicianTaskAssignment);

	List<BasicMaintainanceOutputDTO> findMyAssigns(String username);
	
	List<BasicMaintainanceOutputDTO> findMyOwnership(String username);

	MaintainanceOutputDTO changeStatus(long id, MaintainanceStatus status);

	MaintainanceOutputDTO addNotes(long id, NotesInputDTO notesInput);

	List<Maintainance> findByReportedAt(LocalDate from, LocalDate to);
}
