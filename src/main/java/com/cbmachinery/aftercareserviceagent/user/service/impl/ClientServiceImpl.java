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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.auth.dto.UserCredentialInputDTO;
import com.cbmachinery.aftercareserviceagent.auth.model.UserCredential;
import com.cbmachinery.aftercareserviceagent.auth.model.enums.Role;
import com.cbmachinery.aftercareserviceagent.auth.service.UserCredentialService;
import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.email.dto.Email;
import com.cbmachinery.aftercareserviceagent.email.service.EmailService;
import com.cbmachinery.aftercareserviceagent.notification.model.Category;
import com.cbmachinery.aftercareserviceagent.notification.model.Group;
import com.cbmachinery.aftercareserviceagent.notification.sender.NotificationSender;
import com.cbmachinery.aftercareserviceagent.user.dto.BasicUserOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientInputDTO;
import com.cbmachinery.aftercareserviceagent.user.dto.ClientOutputDTO;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.enums.Gender;
import com.cbmachinery.aftercareserviceagent.user.repository.ClientRepository;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {

	private final UserCredentialService userCredentialService;
	private final ClientRepository clientRepository;
	private final NotificationSender notificationSender;
	private final EmailService emailService;

	public ClientServiceImpl(final UserCredentialService userCredentialService, final ClientRepository clientRepository,
			@Lazy final NotificationSender notificationSender, final EmailService emailService) {
		super();
		this.userCredentialService = userCredentialService;
		this.clientRepository = clientRepository;
		this.notificationSender = notificationSender;
		this.emailService = emailService;
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
				.erpId(clientInput.getErpId()).userCredential(userCredential).build();
		Client savedClient = clientRepository.save(clientToSave);

		emailService.sendHtmlEmail(Email.builder().to(clientInput.getEmail())
				.subject("Your account created on CB Aftercare App")
				.body("<html><body>" + "<h3>Dear " + clientInput.getFirstName() + " " + clientInput.getLastName()
						+ "</h3>"
						+ "<p>We have created an account for you on CB Aftercare Mobile Application to manage your product's Maintainances and breakdowns. Please refer below details to login to this App.</p>"
						+ "<h4>Download URL : <a href=''>Android</a></h4>" + "<h4>Username : " + clientInput.getEmail()
						+ "</h4>" + "<h4>Password : " + clientInput.getPassword() + " </h4>" + "<p>Thank you</p>"
						+ "</body>" + "</html>")
				.build());

		return savedClient.viewAsBasicDTO();
	}

	@Override
	public Page<BasicUserOutputDTO> findAll(Pageable pageable, String searchTerm) {
		if (Objects.isNull(searchTerm)) {
			return clientRepository.findAll(pageable).map(Client::viewAsBasicDTO);
		}

		return clientRepository
				.findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrErpIdContainingIgnoreCase(
						searchTerm, searchTerm, searchTerm, searchTerm, pageable)
				.map(Client::viewAsBasicDTO);
	}

	@Override
	public ClientOutputDTO findByIdAsDTO(long id) {
		return findById(id).viewAsDTO();
	}

	@Override
	public Client findById(long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Client found for this ID"));
	}

	@Override
	public List<BasicUserOutputDTO> findAll() {
		return StreamSupport.stream(clientRepository.findAll().spliterator(), false).map(Client::viewAsBasicDTO)
				.collect(Collectors.toList());

	}

	@Override
	public void importFromCSV(MultipartFile csv) {
		CSVParser csvParser;

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(csv.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int row = 0;

			List<ClientInputDTO> clientInputs = new ArrayList<>();
			List<String> usernames = new ArrayList<>();

			for (CSVRecord cr : csvRecords) {
				if (row != 0) {
					usernames.add(cr.get(2));
					clientInputs.add(
							new ClientInputDTO(cr.get(0), cr.get(1), cr.get(2), cr.get(3), Gender.valueOf(cr.get(6)),
									"Zaq1xsw2@", cr.get(7), cr.get(8), cr.get(9), cr.get(10), cr.get(4), cr.get(5)));
				}

				row++;
			}

			// check for duplicate users in the file & system
			Set<String> filteredUsernames = new HashSet<>(usernames);

			if ((filteredUsernames.size() < usernames.size()) || userCredentialService.usernamesExists(usernames)) {
				throw new IllegalArgumentException("Duplicate Usernames inside the file");
			}

			clientInputs.stream().forEach(ci -> save(ci));

			this.notificationSender.send(Group.ADMINISTRATOR, "Clients import has been completed",
					clientInputs.size() + " clients were created", Category.CLIENTS);

			csvParser.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Error in Client import !!!");
		}
	}

	@Override
	public Client findByUsername(String username) {
		return clientRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("No Client found for this ID"));
	}

	@Override
	public long count() {
		return this.clientRepository.count();
	}

}
