package com.api.products.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.api.products.repository.ProductRepository;
import com.api.products.controller.dto.ProductDto;
import com.api.products.model.Product;

import javax.transaction.Transactional;
import javax.validation.Valid;

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
	public ResponseEntity<Product> cadastrar(@RequestBody @Valid Product product, UriComponentsBuilder uriBuilder) {
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
	public ResponseEntity<Product> update(@PathVariable(value = "id") String id, @RequestBody Product productForm) {
		Optional<Product> findproduct = productRepository.findById(id);
		if (findproduct.isPresent()) {
			return ResponseEntity.ok(productForm.update(id, productRepository));
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

	
	
	@GetMapping("/search")
	public List<ProductDto> getWithFilter( @RequestParam(required = false) String min_price,  @RequestParam(required = false) String max_price, @RequestParam(required = false) String q) {
		List<Product> products;
		if(q==null && min_price != null && max_price!=null) {
			Double min = Double.parseDouble(min_price);
			Double max = Double.parseDouble(max_price);
			products = productRepository.findByPrice(min, max);
			if(!products.isEmpty()) {
				return ProductDto.convert(products); 
			}
		}else if (q!=null && min_price == null && max_price==null) {
			products = productRepository.findProductByNameOrDescription(q);
			return ProductDto.convert(products);
		}else {
			Double min = Double.parseDouble(min_price);
			Double max = Double.parseDouble(max_price);
			products = productRepository.findByPrice(min, max);
			if(!products.isEmpty()) {
				List<Product> productSelected = new ArrayList<Product>();;
				for(Product product : products){
		            if(product.getName().equals(q) || product.getDescription().equals(q)) {
		            	productSelected.add(product);
		            }
		        }
				return ProductDto.convert(productSelected); 
			}
			
		}
//		return "min_price: " + min_price + " max_price: " + max_price + " name: " + q;
		return ProductDto.convert(products);
	}
}