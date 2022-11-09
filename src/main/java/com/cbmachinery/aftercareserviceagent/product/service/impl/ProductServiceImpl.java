package com.cbmachinery.aftercareserviceagent.product.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
				.description(productInput.getDescription()).countryOfOrigin(productInput.getCountryOfOrigin())
				.make(productInput.getMake()).model(productInput.getModel())
				.manufactureYear(productInput.getManufactureYear()).serialNumber(productInput.getSerialNumber())
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
		return productRepository.findByParams(searchTerm, searchTerm, searchTerm, searchTerm, pageable)
				.map(Product::viewAsBasicDTO);
	}

	@Override
	public List<BasicProductOutputDTO> findAll() {
		return StreamSupport.stream(productRepository.findAll().spliterator(), false).map(Product::viewAsBasicDTO)
				.collect(Collectors.toList());
	}

	@Override
	public ProductOutputDTO findByIdAsDTO(long id) {
		return findById(id).viewAsDTO();
	}

	@Override
	public void importFromCSV(MultipartFile csv) {
		CSVParser csvParser;

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(csv.getInputStream(), "UTF-8"));
			csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT);
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			int row = 0;

			List<ProductInputDTO> productsInputs = new ArrayList<>();

			for (CSVRecord cr : csvRecords) {
				if (row != 0) {
					Client client = this.clientService.findByUsername(cr.get(4));
					productsInputs.add(new ProductInputDTO(cr.get(0), Integer.parseInt(cr.get(1)),
							Integer.parseInt(cr.get(2)), cr.get(3), client.getId(), cr.get(5), cr.get(6), cr.get(7),
							cr.get(8), Year.of(Integer.parseInt(cr.get(9))), cr.get(10)));
				}

				row++;
			}

			productsInputs.stream().forEach(pi -> save(pi));

			csvParser.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Error in Client import !!!");
		}
	}

	@Override
	public List<Product> findByClient(Client client) {
		return this.productRepository.findByClient(client);
	}

	@Override
	public List<BasicProductOutputDTO> findMyProducts(String username) {
		return this.productRepository.findAll().stream().filter(p -> p.getClient().getEmail().equals(username))
				.sorted(Comparator.comparing(Product::getModifiedAt)).map(Product::viewAsBasicDTO)
				.collect(Collectors.toList());
	}

	@Override
	public long count() {
		return this.productRepository.count();
	}

	@Override
	public Product findByErpId(String erpId) {
		return productRepository.findByErpId(erpId)
				.orElseThrow(() -> new ResourceNotFoundException("No Product found for this ERP ID"));
	}

}
