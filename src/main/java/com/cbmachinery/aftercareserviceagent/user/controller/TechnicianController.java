package com.cbmachinery.aftercareserviceagent.user.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@RestController
@RequestMapping("/api/users/technicians")
public class TechnicianController {

	private final TechnicianService technicianService;

	public TechnicianController(final TechnicianService technicianService) {
		super();
		this.technicianService = technicianService;
	}

	@PostMapping
	public ResponseEntity<BasicUserOutputDTO> save(@Valid @RequestBody TechnicianInputDTO technicianInput) {
		return new ResponseEntity<>(technicianService.save(technicianInput), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<BasicUserOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(technicianService.findAll(pageable, searchTerm));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TechnicianOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(technicianService.findById(id));
	}
}
