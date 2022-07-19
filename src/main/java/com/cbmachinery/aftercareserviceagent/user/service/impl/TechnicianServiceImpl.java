package com.cbmachinery.aftercareserviceagent.user.service.impl;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;
import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
import com.cbmachinery.aftercareserviceagent.user.repository.TechnicianRepository;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@Service
public class TechnicianServiceImpl implements TechnicianService {

	private final UserCredentialService userCredentialService;
	private final TechnicianRepository technicianRepository;

	public TechnicianServiceImpl(final UserCredentialService userCredentialService,
			final TechnicianRepository technicianRepository) {
		super();
		this.userCredentialService = userCredentialService;
		this.technicianRepository = technicianRepository;
	}

	@Override
	public BasicUserOutputDTO save(TechnicianInputDTO technicianInput) {
		UserCredential userCredential = userCredentialService.save(
				new UserCredentialInputDTO(technicianInput.getEmail(), technicianInput.getPassword(), Role.CLIENT));

		Technician technicianToSave = Technician.builder().yearOfExperience(technicianInput.getYearOfExperience())
				.email(technicianInput.getEmail()).firstName(technicianInput.getFirstName())
				.gender(technicianInput.getGender()).lastName(technicianInput.getLastName())
				.primaryPhoneNo(technicianInput.getPrimaryPhoneNo()).userCredential(userCredential).build();
		return technicianRepository.save(technicianToSave).viewAsBasicDTO();
	}

	@Override
	public Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm) {
		if (Objects.isNull(searchTerm)) {
			return technicianRepository.findAll(pageable).map(Technician::viewAsBasicDTO);
		}

		return technicianRepository
				.findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
						searchTerm, searchTerm, searchTerm, pageable)
				.map(Technician::viewAsBasicDTO);
	}

	@Override
	public TechnicianOutputDTO findById(long id) {
		return technicianRepository.findById(id).map(Technician::viewAsDTO)
				.orElseThrow(() -> new ResourceNotFoundException("No Technician found for this ID"));
	}

}
