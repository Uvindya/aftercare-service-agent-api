package com.cbmachinery.aftercareserviceagent.product.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductInputDTO;
import com.cbmachinery.aftercareserviceagent.product.dto.ProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(final ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<BasicProductOutputDTO> save(@Valid @RequestBody ProductInputDTO productInput) {
		return new ResponseEntity<>(productService.save(productInput), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Page<BasicProductOutputDTO>> findAll(Pageable pageable,
			@RequestParam(required = false) String searchTerm) {
		return ResponseEntity.ok(productService.findAll(pageable, searchTerm));
	}

	@GetMapping("/all")
	public ResponseEntity<List<BasicProductOutputDTO>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductOutputDTO> findById(@PathVariable long id) {
		return ResponseEntity.ok(productService.findByIdAsDTO(id));
	}

	@PostMapping("/import")
	public ResponseEntity<BasicProductOutputDTO> importFromCSV(@RequestParam("file") MultipartFile file) {
		productService.importFromCSV(file);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@GetMapping("/my")
	public ResponseEntity<List<BasicProductOutputDTO>> findMyProducts() {
		return ResponseEntity
				.ok(productService.findMyProducts(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

}
