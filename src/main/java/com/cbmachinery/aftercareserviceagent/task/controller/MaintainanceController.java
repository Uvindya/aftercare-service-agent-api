package com.cbmachinery.aftercareserviceagent.task.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;

@RestController
@RequestMapping("/api/tasks/maintainances")
public class MaintainanceController {

	private final MaintainanceService maintainanceService;

	public MaintainanceController(final MaintainanceService maintainanceService) {
		super();
		this.maintainanceService = maintainanceService;
	}

	@GetMapping
	public ResponseEntity<Page<BasicMaintainanceOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(maintainanceService.findAll(pageable, searchTerm));
	}

	@GetMapping("/{id}")
	public ResponseEntity<MaintainanceOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(maintainanceService.findById(id));
	}

	@PutMapping("/assign")
	public ResponseEntity<MaintainanceOutputDTO> assignTechnician(
			@RequestBody TechnicianTaskAssignmentDTO technicianTaskAssignment) {
		return ResponseEntity.ok(maintainanceService.assignTechnician(technicianTaskAssignment));
	}

}
