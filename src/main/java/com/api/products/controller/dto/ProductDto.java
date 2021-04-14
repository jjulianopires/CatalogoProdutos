package com.api.products.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.api.products.model.Product;

public class ProductDto {
	private String id;
	private String name;
	private String description;
	private double price;

	
	public ProductDto(Product product) {
		super();
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public static List<ProductDto> convert (List<Product> product){
		return product.stream().map(ProductDto::new).collect(Collectors.toList());
				
	}
}
