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

import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownNotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.service.BreakdownService;

@RestController
@RequestMapping("/api/tasks/breakdowns")
public class BreakdownController {
	private final BreakdownService breakdownService;

	public BreakdownController(final BreakdownService breakdownService) {
		super();
		this.breakdownService = breakdownService;
	}

	@PostMapping
	public ResponseEntity<BasicBreakdownOutputDTO> save(@Valid @RequestBody BreakdownInputDTO breakdownInput) {
		return new ResponseEntity<>(breakdownService.save(breakdownInput), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<BasicBreakdownOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(breakdownService.findAll(pageable, searchTerm));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BreakdownOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(breakdownService.findById(id));
	}

	@PutMapping("/assign")
	public ResponseEntity<BreakdownOutputDTO> assignTechnician(
			@RequestBody TechnicianTaskAssignmentDTO technicianTaskAssignment) {
		return ResponseEntity.ok(breakdownService.assignTechnician(technicianTaskAssignment));
	}

	@PutMapping("/{id}/start")
	public ResponseEntity<BreakdownOutputDTO> startMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(breakdownService.start(id, BreakdownStatus.IN_PROGRESS));
	}

	@PutMapping("/{id}/complete")
	public ResponseEntity<BreakdownOutputDTO> completeMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(breakdownService.complete(id, BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE));
	}

	@PutMapping("/{id}/accept")
	public ResponseEntity<BreakdownOutputDTO> acceptMaintainance(@PathVariable long id) {
		return ResponseEntity.ok(breakdownService.changeStatus(id, BreakdownStatus.COMPLETED));
	}

	@PutMapping("/{id}/notes")
	public ResponseEntity<BreakdownOutputDTO> addMaintainanceNotes(@PathVariable long id,
			@Valid @RequestBody BreakdownNotesInputDTO notesInput) {
		return ResponseEntity.ok(breakdownService.addNotes(id, notesInput));
	}

	@GetMapping("/my-assigns")
	public ResponseEntity<List<BasicBreakdownOutputDTO>> findMyAssigns() {
		return ResponseEntity
				.ok(breakdownService.findMyAssigns(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@GetMapping("/my-ownerships")
	public ResponseEntity<List<BasicBreakdownOutputDTO>> findMyOwnerships() {
		return ResponseEntity
				.ok(breakdownService.findMyOwnership(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@PostMapping("/import")
	public ResponseEntity<Void> importFromCSV(@RequestParam("file") MultipartFile file) {
		breakdownService.importFromCSV(file);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
}
