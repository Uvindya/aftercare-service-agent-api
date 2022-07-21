package com.cbmachinery.aftercareserviceagent.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cbmachinery.aftercareserviceagent.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(value = "SELECT p.* FROM products p INNER JOIN clients c ON c.id=p.client_id WHERE p.name LIKE CONCAT('%',:name,'%') OR p.erp_id LIKE CONCAT('%',:erpId,'%') OR c.first_name LIKE CONCAT('%',:clientName,'%') OR c.last_name LIKE CONCAT('%',:clientName,'%')", nativeQuery = true, countQuery = "SELECT count(*) FROM products p INNER JOIN clients c ON c.id=p.client_id WHERE p.name LIKE CONCAT('%',:name,'%') OR p.erp_id LIKE CONCAT('%',:erpId,'%') OR c.first_name LIKE CONCAT('%',:clientName,'%') OR c.last_name LIKE CONCAT('%',:clientName,'%')")
	Page<Product> findByParams(String name, String erpId, String clientName, Pageable pageable);
}
