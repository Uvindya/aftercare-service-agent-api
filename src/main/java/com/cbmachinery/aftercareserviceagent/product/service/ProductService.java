package com.cbmachinery.aftercareserviceagent.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductInputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.user.model.Client;

public interface ProductService {
	BasicProductOutputDTO findByIdAsBasicDTO(long id);

	ProductOutputDTO findByIdAsDTO(long id);

	Product findById(long id);

	BasicProductOutputDTO save(ProductInputDTO productInput);

	Page<BasicProductOutputDTO> findAll(Pageable pageable, String searchTerm);

	void importFromCSV(MultipartFile csv);
	
	List<Product> findByClient(Client client);
}
