package com.cbmachinery.aftercareserviceagent.task.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.NotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
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

	@PutMapping("/{id}/start")
	public ResponseEntity<MaintainanceOutputDTO> startMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(maintainanceService.start(id, MaintainanceStatus.IN_PROGRESS));
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<MaintainanceOutputDTO> completeMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(maintainanceService.complete(id, MaintainanceStatus.NEEDS_CLIENTS_ACCEPTENCE));
	}

	@PutMapping("/{id}/approve")
	public ResponseEntity<MaintainanceOutputDTO> approveMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(maintainanceService.approve(id, MaintainanceStatus.CLIENT_ACKNOWLEDGED));
	}

	@PutMapping("/{id}/accept")
	public ResponseEntity<MaintainanceOutputDTO> acceptMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(maintainanceService.changeStatus(id, MaintainanceStatus.COMPLETED));
	}

	@PutMapping("/{id}/notes")
	public ResponseEntity<MaintainanceOutputDTO> addMaintainanceNotes(@PathVariable long id,
			@Valid @RequestBody NotesInputDTO notesInput) {
		return ResponseEntity.ok(maintainanceService.addNotes(id, notesInput));
	}

	@GetMapping("/my-assigns")
	public ResponseEntity<List<BasicMaintainanceOutputDTO>> findMyAssigns() {
		return ResponseEntity.ok(
				maintainanceService.findMyAssigns(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@GetMapping("/my-ownerships")
	public ResponseEntity<List<BasicMaintainanceOutputDTO>> findMyOwnerships() {
		return ResponseEntity.ok(
				maintainanceService.findMyOwnership(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@PostMapping("/import")
	public ResponseEntity<Void> importFromCSV(@RequestParam("file") MultipartFile file) {
		maintainanceService.importFromCSV(file);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

}
