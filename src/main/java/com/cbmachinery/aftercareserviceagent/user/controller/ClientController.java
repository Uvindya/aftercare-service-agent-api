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
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;

@RestController
@RequestMapping("/api/users/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(final ClientService clientService) {
		super();
		this.clientService = clientService;
	}

	@PostMapping
	public ResponseEntity<BasicUserOutputDTO> saveClient(@Valid @RequestBody ClientInputDTO clientInput) {
		return new ResponseEntity<>(clientService.save(clientInput), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<BasicUserOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(clientService.findAll(pageable, searchTerm));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(clientService.findById(id));
	}

}
