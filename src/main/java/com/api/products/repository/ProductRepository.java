package com.api.products.repository;

import com.api.products.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

	List<Product> findByName(String name);

	List<Product> findByDescription(String description);

	List<Product> findByPrice(String price);
}
