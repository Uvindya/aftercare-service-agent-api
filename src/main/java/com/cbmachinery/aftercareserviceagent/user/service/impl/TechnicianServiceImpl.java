package com.cbmachinery.aftercareserviceagent.user.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;
import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.TechnicianOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
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
				new UserCredentialInputDTO(technicianInput.getEmail(), technicianInput.getPassword(), Role.TECHNICIAN));

		Technician technicianToSave = Technician.builder().yearOfExperience(technicianInput.getYearOfExperience())
				.email(technicianInput.getEmail()).firstName(technicianInput.getFirstName())
				.gender(technicianInput.getGender()).lastName(technicianInput.getLastName())
				.erpId(technicianInput.getErpId()).primaryPhoneNo(technicianInput.getPrimaryPhoneNo())
				.userCredential(userCredential).build();
		return technicianRepository.save(technicianToSave).viewAsBasicDTO();
	}

	@Override
	public Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm) {
		if (Objects.isNull(searchTerm)) {
			return technicianRepository.findAll(pageable).map(Technician::viewAsBasicDTO);
		}

		return technicianRepository
				.findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrErpIdContainingIgnoreCase(
						searchTerm, searchTerm, searchTerm, searchTerm, pageable)
				.map(Technician::viewAsBasicDTO);
	}

	@Override
	public TechnicianOutputDTO findById(long id) {
		return technicianRepository.findById(id).map(Technician::viewAsDTO)
				.orElseThrow(() -> new ResourceNotFoundException("No Technician found for this ID"));
	}

	@Override
	public void importFromCSV(MultipartFile csv) {
		CSVParser csvParser;

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(csv.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int row = 0;

			List<TechnicianInputDTO> technicianInputs = new ArrayList<>();
			List<String> usernames = new ArrayList<>();

			for (CSVRecord cr : csvRecords) {
				if (row != 0) {
					usernames.add(cr.get(2));
					technicianInputs.add(new TechnicianInputDTO(cr.get(0), cr.get(1), cr.get(2), cr.get(3),
							Gender.valueOf(cr.get(5)), "Zaq1xsw2@", Integer.valueOf(cr.get(6)), cr.get(4)));
				}

				row++;
			}

			// check for duplicate users in the file & system
			Set<String> filteredUsernames = new HashSet<>(usernames);

			if ((filteredUsernames.size() < usernames.size()) || userCredentialService.usernamesExists(usernames)) {
				throw new IllegalArgumentException("Duplicate Usernames inside the file");
			}

			technicianInputs.stream().forEach(ti -> save(ti));

			csvParser.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Error in Technician import !!!");
		}
	}

	@Override
	public List<BasicUserOutputDTO> findAll() {
		return StreamSupport.stream(technicianRepository.findAll().spliterator(), false).map(Technician::viewAsBasicDTO)
				.collect(Collectors.toList());

	}

	@Override
	public Technician findByIdAsRaw(long id) {
		return technicianRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Technician found for this ID"));
	}

}
