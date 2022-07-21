package com.cbmachinery.aftercareserviceagent.product.service.impl;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.common.exception.ResourceNotFoundException;
import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductInputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.product.repository.ProductRepository;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;
import com.cbmachinery.aftercareserviceagent.util.ProductMaintainanceUtil;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ClientService clientService;
	private final ProductMaintainanceUtil productMaintainanceUtil;

	public ProductServiceImpl(final ProductRepository productRepository, final ClientService clientService,
			final ProductMaintainanceUtil productMaintainanceUtil) {
		super();
		this.productRepository = productRepository;
		this.clientService = clientService;
		this.productMaintainanceUtil = productMaintainanceUtil;
	}

	@Override
	public BasicProductOutputDTO findByIdAsBasicDTO(long id) {
		return findById(id).viewAsBasicDTO();
	}

	@Override
	public Product findById(long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Product found for this ID"));

	}

	@Override
	public BasicProductOutputDTO save(ProductInputDTO productInput) {
		Client client = clientService.findById(productInput.getClientId());

		Product productToSave = Product.builder().client(client).erpId(productInput.getErpId())
				.maintainnanceInterval(productInput.getMaintainnanceInterval()).name(productInput.getName())
				.warrentyPeriod(productInput.getWarrentyPeriod()).build();

		Product savedProduct = this.productRepository.save(productToSave);

		productMaintainanceUtil.scheduleMaintainances(savedProduct.getId(), savedProduct.getCreatedAt(),
				savedProduct.getWarrentyPeriod(), savedProduct.getMaintainnanceInterval());

		return savedProduct.viewAsBasicDTO();
	}

	@Override
	public Page<BasicProductOutputDTO> findAll(Pageable pageable, String searchTerm) {
		if (Objects.isNull(searchTerm)) {
		return productRepository.findAll(pageable).map(Product::viewAsBasicDTO);
		}
		return productRepository.findByParams(searchTerm, searchTerm, searchTerm,pageable).map(Product::viewAsBasicDTO);
	}

	@Override
	public ProductOutputDTO findByIdAsDTO(long id) {
		return findById(id).viewAsDTO();
	}

}
