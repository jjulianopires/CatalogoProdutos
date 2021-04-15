package com.api.products.controller.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;


import com.api.products.model.Product;
import com.api.products.repository.ProductRepository;

public class ProductFormDto {
	@NotNull
	@NotEmpty
	private String name;
	@NotNull
	@NotEmpty
	private String description;
	@NotNull
	@Positive
	private double price;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Product update(String id, ProductRepository productRepository) {
		Product product = productRepository.getOne(id);
		product.setName(this.name);
		product.setDescription(this.description);
		product.setPrice(this.price);
		return product;
	}
	
}
