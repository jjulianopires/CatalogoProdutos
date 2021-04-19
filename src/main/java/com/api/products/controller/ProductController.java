package com.api.products.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.products.controller.dto.ProductDto;
import com.api.products.controller.dto.ProductFormDto;
import com.api.products.model.Product;
import com.api.products.repository.ProductRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository = null;

	@GetMapping
	public List<ProductDto> listProducts() {
		List<Product> product = productRepository.findAll();
		return ProductDto.convert(product);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Product> create(@RequestBody @Valid ProductFormDto productFormDto,
			UriComponentsBuilder uriBuilder) {
		Product product = new Product(productFormDto.getName(), productFormDto.getDescription(),
				productFormDto.getPrice());
		productRepository.save(product);
		URI uri = uriBuilder.path("/products/{id}").buildAndExpand(product).toUri();
		return ResponseEntity.created(uri).body(product);

	}

	@GetMapping("{id}")
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable(value = "id") String id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<Product> update(@PathVariable(value = "id") String id,
			@RequestBody @Valid ProductFormDto productFormDto) {
		Optional<Product> findproduct = productRepository.findById(id);
		if (findproduct.isPresent()) {
			return ResponseEntity.ok(productFormDto.update(id, productRepository));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<Product> remove(@PathVariable(value = "id") String id) {
		Optional<Product> findproduct = productRepository.findById(id);
		if (findproduct.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Resgata uma lista contendo todos os produtos já cadastrados e filtrados por parâmetros")
	@GetMapping("/search")
	public ResponseEntity<?> getWithFilter(
			@RequestParam(required = false) @ApiParam(value = "Param search by min price") BigDecimal min_price,
			@RequestParam(required = false) @ApiParam(value = "Param search by max price") BigDecimal max_price,
			@RequestParam(required = false) @ApiParam(value = "Param search by 'name' and 'description'") String q) {

		List<Product> products = new ArrayList<>();

		try {
			if (min_price == null && max_price == null) {
				products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(q, q);
				return new ResponseEntity<>(ProductDto.convert(products), HttpStatus.OK);
			} else {
				if (min_price == null || max_price == null) {
					return new ResponseEntity<>(products, HttpStatus.OK);
				}

				if (min_price.compareTo(max_price) == 1) {
					throw new Exception();
				} else {
					products = productRepository.findProductByNameOrDescriptionAndPrice(q, min_price, max_price);
					return new ResponseEntity<>(ProductDto.convert(products), HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}

	}
}
