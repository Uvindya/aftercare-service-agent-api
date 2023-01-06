package com.cbmachinery.aftercareserviceagent.user.controller;

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

import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientChangePasswordDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientProfileDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientUpdateDTO;
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

	@PutMapping("/{id}")
	public ResponseEntity<BasicUserOutputDTO> updateClient(@PathVariable long id,
			@Valid @RequestBody ClientUpdateDTO clientInput) {
		return ResponseEntity.ok(clientService.update(id, clientInput));
	}

	@PostMapping("/import")
	public ResponseEntity<BasicUserOutputDTO> importFromCSV(@RequestParam("file") MultipartFile file) {
		clientService.importFromCSV(file);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<BasicUserOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(clientService.findAll(pageable, searchTerm));
	}

	@GetMapping("/all")
	public ResponseEntity<List<BasicUserOutputDTO>> findAll() {
		return ResponseEntity.ok(clientService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(clientService.findByIdAsDTO(id));
	}

	@GetMapping("/profile")
	public ResponseEntity<ClientProfileDTO> findProfile() {
		return ResponseEntity
				.ok(clientService.findProfile(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Void> resetPassword(@Valid @RequestBody ClientChangePasswordDTO clientResetPasswordDTO) {
		clientService.changePassword(SecurityContextHolder.getContext().getAuthentication().getName(),
				clientResetPasswordDTO);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}
	


	@PutMapping("/resetpassword/{id}")
	public ResponseEntity<Void> resetPassword(@PathVariable long id) {
		clientService.resetPassword(id);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}
}
