package com.cbmachinery.aftercareserviceagent.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductInputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductUpdateDTO;
import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.user.model.Client;

public interface ProductService {
	BasicProductOutputDTO findByIdAsBasicDTO(long id);

	ProductOutputDTO findByIdAsDTO(long id);

	Product findById(long id);

	BasicProductOutputDTO save(ProductInputDTO productInput);

	BasicProductOutputDTO update(long id, ProductUpdateDTO productInput);

	Page<BasicProductOutputDTO> findAll(Pageable pageable, String searchTerm);

	List<BasicProductOutputDTO> findAll();

	List<BasicProductOutputDTO> findMyProducts(String username);

	void importFromCSV(MultipartFile csv);

	List<Product> findByClient(Client client);

	long count();

	Product findByErpId(String erpId);
}
