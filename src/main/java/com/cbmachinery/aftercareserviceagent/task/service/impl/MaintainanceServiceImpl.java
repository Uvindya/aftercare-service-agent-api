package com.cbmachinery.aftercareserviceagent.task.service.impl;

import java.time.LocalDate;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceInputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.MaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.repository.MaintainanceRepository;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;

@Service
public class MaintainanceServiceImpl implements MaintainanceService {

	private final MaintainanceRepository maintainanceRepository;
	private final ProductService productService;

	public MaintainanceServiceImpl(final MaintainanceRepository maintainanceRepository,
			@Lazy ProductService productService) {
		super();
		this.maintainanceRepository = maintainanceRepository;
		this.productService = productService;
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
		//if (Objects.isNull(searchTerm)) {
			return maintainanceRepository.findAll(pageable).map(Maintainance::viewAsBasicDTO);
		//}

		//return null;

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

}
