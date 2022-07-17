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
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.repository.ClientRepository;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private final UserCredentialService userCredentialService;
	private final ClientRepository clientRepository;

	public ClientServiceImpl(final UserCredentialService userCredentialService,
			final ClientRepository clientRepository) {
		super();
		this.userCredentialService = userCredentialService;
		this.clientRepository = clientRepository;
	}

	@Override
	public BasicUserOutputDTO save(ClientInputDTO clientInput) {
		UserCredential userCredential = userCredentialService
				.save(new UserCredentialInputDTO(clientInput.getEmail(), clientInput.getPassword(), Role.CLIENT));
		Client clientToSave = Client.builder().addressLine1(clientInput.getAddressLine1())
				.addressLine2(clientInput.getAddressLine2()).city(clientInput.getCity())
				.district(clientInput.getDistrict()).email(clientInput.getEmail()).firstName(clientInput.getFirstName())
				.gender(clientInput.getGender()).lastName(clientInput.getLastName())
				.primaryPhoneNo(clientInput.getPrimaryPhoneNo()).secondaryPhoneNo(clientInput.getSecondaryPhoneNo())
				.userCredential(userCredential).build();
		return clientRepository.save(clientToSave).viewAsBasicDTO();
	}

	@Override
	public Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm) {
		if (Objects.isNull(searchTerm)) {
			return clientRepository.findAll(pageable).map(Client::viewAsBasicDTO);
		}

		return clientRepository
				.findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
						searchTerm, searchTerm, searchTerm, pageable)
				.map(Client::viewAsBasicDTO);
	}

	@Override
	public ClientOutputDTO findById(long id) {
		return clientRepository.findById(id).map(Client::viewAsDTO)
				.orElseThrow(() -> new ResourceNotFoundException("No Client found for this ID"));
	}

}
