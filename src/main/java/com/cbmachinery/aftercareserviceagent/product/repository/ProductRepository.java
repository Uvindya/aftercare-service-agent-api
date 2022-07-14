package com.cbmachinery.aftercareserviceagent.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbmachinery.aftercareserviceagent.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
