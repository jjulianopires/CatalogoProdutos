package com.api.products.repository;

import com.api.products.model.Product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


	@Query("from Product where price between :min and :max")
	List<Product> findByPrice(@Param("min") double min, @Param("max") double max);
	

@Query("SELECT u FROM Product u WHERE (upper(u.name) LIKE CONCAT(upper(?1)) or upper(u.description) LIKE CONCAT(upper(?1)))")
List<Product> findProductByNameOrDescription(String nameOrDescription);

@Query("SELECT u FROM Product u WHERE (upper(u.name) LIKE CONCAT('%',upper(?1),'%') or upper(u.description) LIKE CONCAT('%',upper(?1),'%')) AND u.price between ?2 and ?3")
List<Product> findProductByNameOrDescriptionAndPrice(String nameOrDescription, double min, double max);


List<Product> findByNameContainingIgnoreCase(String name);
List<Product> findByDescriptionContainingIgnoreCase(String description);
List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}

