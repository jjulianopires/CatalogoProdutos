package com.api.products.repository;

import com.api.products.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


	@Query("from Product where price between :min and :max")
	List<Product> findByPrice(@Param("min") double min, @Param("max") double max);
	
	@Query("SELECT u FROM Product u WHERE u.name = ?1 or u.description = ?1")
	List<Product> findProductByNameOrDescription(String nameOrDescription);
	
	}
