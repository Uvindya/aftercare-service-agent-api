package com.cbmachinery.aftercareserviceagent.task.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.common.util.DateTimeUtil;
import com.cbmachinery.aftercareserviceagent.notification.model.Category;
import com.cbmachinery.aftercareserviceagent.notification.model.Group;
import com.cbmachinery.aftercareserviceagent.notification.sender.NotificationSender;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownNotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownType;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Priority;
import com.cbmachinery.aftercareserviceagent.task.model.enums.Risk;
import com.cbmachinery.aftercareserviceagent.task.repository.BreakdownRepository;
import com.cbmachinery.aftercareserviceagent.task.service.BreakdownService;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@Service
public class BreakdownServiceImpl implements BreakdownService {

	private final BreakdownRepository breakdownRepository;
	private final ProductService productService;
	private final TechnicianService technicianService;
	private final ClientService clientService;
	private final NotificationSender notificationSender;

	public BreakdownServiceImpl(final BreakdownRepository breakdownRepository, @Lazy ProductService productService,
			@Lazy TechnicianService technicianService, @Lazy ClientService clientService,
			@Lazy final NotificationSender notificationSender) {
		super();
		this.breakdownRepository = breakdownRepository;
		this.productService = productService;
		this.technicianService = technicianService;
		this.clientService = clientService;
		this.notificationSender = notificationSender;
	}

	@Override
	public BasicBreakdownOutputDTO save(BreakdownInputDTO breakdownInput) {
		Product product = this.productService.findById(breakdownInput.getProductId());

		Breakdown breakdownToSave = Breakdown.builder().breakdownType(breakdownInput.getBreakdownType())
				.description(breakdownInput.getDescription()).priorityLevel(breakdownInput.getPriorityLevel())
				.product(product).reportedAt(LocalDate.now()).riskLevel(breakdownInput.getRiskLevel())
				.scheduledDate(breakdownInput.getScheduledDate()).status(BreakdownStatus.NEW).build();

		Breakdown savedBreakdown = this.breakdownRepository.save(breakdownToSave);

		this.notificationSender.send(Group.ADMINISTRATOR,
				product.getClient().getUserCredential().getUsername() + " has reported a breakdown",
				"ID - " + savedBreakdown.getId() + ", Risk - " + savedBreakdown.getRiskLevel().name() + ", Priority -  "
						+ savedBreakdown.getPriorityLevel().name() + ", Type - "
						+ savedBreakdown.getBreakdownType().name(),
				Category.BREAKDOWN);

		return savedBreakdown.viewAsBasicDTO();
	}

	@Override
	public Page<BasicBreakdownOutputDTO> findAll(Pageable pageable, String searchTerm) {
		// if (Objects.isNull(searchTerm)) {
		return breakdownRepository.findAll(pageable).map(Breakdown::viewAsBasicDTO);
		// }

		// return null;

		/*
		 * return technicianRepository
		 * .findAllByEmailContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
		 * searchTerm, searchTerm, searchTerm, pageable)
		 * .map(Technician::viewAsBasicDTO);
		 */
	}

	@Override
	public BreakdownOutputDTO findById(long id) {
		return breakdownRepository.findById(id).map(Breakdown::viewAsDTO)
				.orElseThrow(() -> new ResourceNotFoundException("No Breakdown found for this ID"));
	}

	@Override
	@Transactional
	public BreakdownOutputDTO assignTechnician(TechnicianTaskAssignmentDTO technicianTaskAssignment) {
		Technician technician = this.technicianService.findByIdAsRaw(technicianTaskAssignment.getTechnicianId());
		breakdownRepository.assignTechnician(technicianTaskAssignment.getTaskId(), technician,
				BreakdownStatus.TECH_ASSIGNED, LocalDateTime.now());
		return findById(technicianTaskAssignment.getTaskId());
	}

	@Override
	public List<BasicBreakdownOutputDTO> findMyAssigns(String username) {
		Technician technician = technicianService.findByUsername(username);
		return technician.getAssignedBreakdowns().stream().sorted(Comparator.comparing(Breakdown::getModifiedAt))
				.map(Breakdown::viewAsBasicDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public BreakdownOutputDTO changeStatus(long id, BreakdownStatus status) {
		breakdownRepository.changeStatus(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public BreakdownOutputDTO start(long id, BreakdownStatus status) {
		breakdownRepository.start(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public BreakdownOutputDTO complete(long id, BreakdownStatus status) {
		breakdownRepository.complete(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public BreakdownOutputDTO addNotes(long id, BreakdownNotesInputDTO notesInput) {
		breakdownRepository.addNotes(id, notesInput.getCompletionNote(), notesInput.getAdditionalNote(),
				notesInput.getRootCause(), notesInput.getSolution(), LocalDateTime.now());
		return findById(id);
	}

	@Override
	public List<BasicBreakdownOutputDTO> findMyOwnership(String username) {
		Client client = clientService.findByUsername(username);
		List<Product> products = productService.findByClient(client);
		return this.breakdownRepository.findByProductIn(products).stream()
				.sorted(Comparator.comparing(Breakdown::getModifiedAt)).map(Breakdown::viewAsBasicDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<Breakdown> findByReportedAt(LocalDate from, LocalDate to) {
		return this.breakdownRepository.findByReportedAtBetween(from, to);
	}

	@Override
	public List<Breakdown> findByAssignedAt(LocalDateTime from, LocalDateTime to) {
		return this.breakdownRepository.findByAssignedAtBetween(from, to);
	}

	@Override
	public List<Breakdown> findByAssignedAtForTechnician(LocalDateTime from, LocalDateTime to, long technicianId) {
		return this.breakdownRepository.findByAssignedAtBetweenForTechnician(from, to, technicianId);
	}

	@Override
	public long count() {
		return this.breakdownRepository.count();
	}

	@Override
	public long inProgressCount() {
		return this.breakdownRepository
				.countByStatusIn(Arrays.asList(BreakdownStatus.IN_PROGRESS, BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE));
	}

	@Override
	public long pendingCount() {
		return this.breakdownRepository.countByStatusIn(
				Arrays.asList(BreakdownStatus.IN_PROGRESS, BreakdownStatus.NEW, BreakdownStatus.TECH_ASSIGNED));
	}

	@Override
	public long pendingAcceptenceCount() {
		return this.breakdownRepository.countByStatusIn(Arrays.asList(BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE));
	}

	@Override
	public long notStartedCount() {
		return this.breakdownRepository
				.countByStatusIn(Arrays.asList(BreakdownStatus.NEW, BreakdownStatus.TECH_ASSIGNED));
	}

	@Override
	public long completedCount() {
		return this.breakdownRepository.countByStatusIn(Arrays.asList(BreakdownStatus.COMPLETED));
	}

	@Override
	public void importFromCSV(MultipartFile csv) {
		CSVParser csvParser;

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(csv.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int row = 0;

			List<Breakdown> breakdownCSVRows = new ArrayList<>();

			for (CSVRecord cr : csvRecords) {
				if (row != 0) {
					Product product = productService.findByErpId(cr.get(8));
					Technician technician = technicianService.findByErpId(cr.get(9));
					breakdownCSVRows.add(Breakdown.builder().erpId(cr.get(0)).description(cr.get(1))
							.reportedAt(DateTimeUtil.fomatToLongDate(cr.get(2)))
							.startedAt(DateTimeUtil.fomatToLongDateTime(cr.get(4)))
							.assignedAt(DateTimeUtil.fomatToLongDateTime(cr.get(3)))
							.completedAt(DateTimeUtil.fomatToLongDateTime(cr.get(5)))
							.scheduledDate(DateTimeUtil.fomatToLongDate(cr.get(6)))
							.targetCompletionDate(DateTimeUtil.fomatToLongDate(cr.get(7))).product(product)
							.technician(technician).completionNote(cr.get(10)).additionalNote(cr.get(11))
							.breakdownType(BreakdownType.valueOf(cr.get(12)))
							.status(BreakdownStatus.valueOf(cr.get(13))).riskLevel(Risk.valueOf(cr.get(14)))
							.priorityLevel(Priority.valueOf(cr.get(15))).solution(cr.get(16)).rootCause(cr.get(17))
							.build());
				}

				row++;
			}

			breakdownCSVRows.stream().forEach(b -> this.breakdownRepository.save(b));

			csvParser.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Error in Client import !!!");
		}
	}
}
