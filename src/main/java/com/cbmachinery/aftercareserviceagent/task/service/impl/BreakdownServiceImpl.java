package com.cbmachinery.aftercareserviceagent.task.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownNotesInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.TechnicianTaskAssignmentDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
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

	public BreakdownServiceImpl(final BreakdownRepository breakdownRepository, @Lazy ProductService productService,
			@Lazy TechnicianService technicianService, @Lazy ClientService clientService) {
		super();
		this.breakdownRepository = breakdownRepository;
		this.productService = productService;
		this.technicianService = technicianService;
		this.clientService = clientService;
	}

	@Override
	public BasicBreakdownOutputDTO save(BreakdownInputDTO breakdownInput) {
		Product product = this.productService.findById(breakdownInput.getProductId());

		Breakdown breakdownToSave = Breakdown.builder().breakdownType(breakdownInput.getBreakdownType())
				.description(breakdownInput.getDescription()).priorityLevel(breakdownInput.getPriorityLevel())
				.product(product).reportedAt(LocalDate.now()).riskLevel(breakdownInput.getRiskLevel())
				.scheduledDate(breakdownInput.getScheduledDate()).status(BreakdownStatus.NEW).build();

		return this.breakdownRepository.save(breakdownToSave).viewAsBasicDTO();
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
}
