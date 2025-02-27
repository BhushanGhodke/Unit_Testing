package com.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//	@Query(value = "")
	public List<Product> findByProductCategory(String categoryName);	
	
	public List<Product> findByProductName(String productName);
}
