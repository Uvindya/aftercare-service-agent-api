package com.cbmachinery.aftercareserviceagent.task.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.NotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.repository.MaintainanceRepository;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@Service
public class MaintainanceServiceImpl implements MaintainanceService {

	private final MaintainanceRepository maintainanceRepository;
	private final ProductService productService;
	private final TechnicianService technicianService;
	private final ClientService clientService;

	public MaintainanceServiceImpl(final MaintainanceRepository maintainanceRepository,
			@Lazy ProductService productService, @Lazy TechnicianService technicianService,
			@Lazy ClientService clientService) {
		super();
		this.maintainanceRepository = maintainanceRepository;
		this.productService = productService;
		this.technicianService = technicianService;
		this.clientService = clientService;
	}

	@Override
	public BasicMaintainanceOutputDTO save(MaintainanceInputDTO maintainanceInput) {
		Product product = this.productService.findById(maintainanceInput.getProductId());

		Maintainance maintainanceToSave = Maintainance.builder().description(maintainanceInput.getDescription())
				.maintainanceType(maintainanceInput.getMaintainanceType()).product(product).reportedAt(LocalDate.now())
				.scheduledDate(maintainanceInput.getScheduledDate()).status(MaintainanceStatus.SCHEDULED).build();

		return this.maintainanceRepository.save(maintainanceToSave).viewAsBasicDTO();
	}

	@Override
	public Page<BasicMaintainanceOutputDTO> findAll(Pageable pageable, String searchTerm) {
		// if (Objects.isNull(searchTerm)) {
		return maintainanceRepository.findAll(pageable).map(Maintainance::viewAsBasicDTO);
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
	public MaintainanceOutputDTO findById(long id) {
		return maintainanceRepository.findById(id).map(Maintainance::viewAsDTO)
				.orElseThrow(() -> new ResourceNotFoundException("No Maintainance found for this ID"));
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO assignTechnician(TechnicianTaskAssignmentDTO technicianTaskAssignment) {
		Technician technician = this.technicianService.findByIdAsRaw(technicianTaskAssignment.getTechnicianId());
		maintainanceRepository.assignTechnician(technicianTaskAssignment.getTaskId(), technician,
				MaintainanceStatus.TECH_ASSIGNED, LocalDateTime.now());
		return findById(technicianTaskAssignment.getTaskId());
	}

	@Override
	public List<BasicMaintainanceOutputDTO> findMyAssigns(String username) {
		Technician technician = technicianService.findByUsername(username);
		return technician.getAssignedMaintainances().stream().sorted(Comparator.comparing(Maintainance::getModifiedAt))
				.map(Maintainance::viewAsBasicDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO approve(long id, MaintainanceStatus status) {
		maintainanceRepository.approve(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO start(long id, MaintainanceStatus status) {
		maintainanceRepository.start(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO complete(long id, MaintainanceStatus status) {
		maintainanceRepository.complete(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO changeStatus(long id, MaintainanceStatus status) {
		maintainanceRepository.changeStatus(id, status, LocalDateTime.now());
		return findById(id);
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO addNotes(long id, NotesInputDTO notesInput) {
		maintainanceRepository.addNotes(id, notesInput.getCompletionNote(), notesInput.getAdditionalNote(),
				LocalDateTime.now());
		return findById(id);
	}

	@Override
	public List<BasicMaintainanceOutputDTO> findMyOwnership(String username) {
		Client client = clientService.findByUsername(username);
		List<Product> products = productService.findByClient(client);
		return this.maintainanceRepository.findByProductIn(products).stream()
				.sorted(Comparator.comparing(Maintainance::getModifiedAt)).map(Maintainance::viewAsBasicDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<Maintainance> findByReportedAt(LocalDate from, LocalDate to) {
		return this.maintainanceRepository.findByReportedAtBetween(from, to);
	}

	@Override
	public List<Maintainance> findByAssignedAt(LocalDateTime from, LocalDateTime to) {
		return this.maintainanceRepository.findByAssignedAtBetween(from, to);
	}

	@Override
	public List<Maintainance> findUpcomming(LocalDate from, LocalDate to) {
		return this.maintainanceRepository.findByScheduledDateBetweenAndStatusNotIn(from, to,
				List.of(MaintainanceStatus.IN_PROGRESS, MaintainanceStatus.NEEDS_CLIENTS_ACCEPTENCE,
						MaintainanceStatus.COMPLETED));
	}

	@Override
	public List<Maintainance> findByAssignedAtForTechnician(LocalDateTime from, LocalDateTime to, long technicianId) {
		return this.maintainanceRepository.findByAssignedAtBetweenForTechnician(from, to, technicianId);
	}

	@Override
	public long count() {
		return this.maintainanceRepository.count();
	}

	@Override
	public long inProgressCount() {
		return this.maintainanceRepository.countByStatusIn(
				Arrays.asList(MaintainanceStatus.IN_PROGRESS, MaintainanceStatus.NEEDS_CLIENTS_ACCEPTENCE));
	}

	@Override
	public long notStartedCount() {
		return this.maintainanceRepository.countByStatusIn(
				Arrays.asList(MaintainanceStatus.CLIENT_ACKNOWLEDGED, MaintainanceStatus.TECH_ASSIGNED));
	}

	@Override
	public long completedCount() {
		return this.maintainanceRepository.countByStatusIn(Arrays.asList(MaintainanceStatus.COMPLETED));
	}

	@Override
	public long scheduledCount() {
		return this.maintainanceRepository.countByStatusIn(Arrays.asList(MaintainanceStatus.SCHEDULED));
	}

	@Override
	public List<Maintainance> findByScheduledAt(LocalDate from, LocalDate to) {
		return this.maintainanceRepository.findByScheduledDateBetween(from, to);
	}

}
