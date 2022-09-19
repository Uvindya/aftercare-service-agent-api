package com.cbmachinery.aftercareserviceagent.task.service.impl;

import java.time.LocalDate;
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
import com.cbmachinery.aftercareserviceagent.user.model.Technician;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@Service
public class MaintainanceServiceImpl implements MaintainanceService {

	private final MaintainanceRepository maintainanceRepository;
	private final ProductService productService;
	private final TechnicianService technicianService;

	public MaintainanceServiceImpl(final MaintainanceRepository maintainanceRepository,
			@Lazy ProductService productService, @Lazy TechnicianService technicianService) {
		super();
		this.maintainanceRepository = maintainanceRepository;
		this.productService = productService;
		this.technicianService = technicianService;
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
				MaintainanceStatus.TECH_ASSIGNED);
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
	public MaintainanceOutputDTO changeStatus(long id, MaintainanceStatus status) {
		maintainanceRepository.changeStatus(id, status);
		return findById(id);
	}

	@Override
	@Transactional
	public MaintainanceOutputDTO addNotes(long id, NotesInputDTO notesInput) {
		maintainanceRepository.addNotes(id, notesInput.getCompletionNote(), notesInput.getAdditionalNote());
		return findById(id);
	}

}
