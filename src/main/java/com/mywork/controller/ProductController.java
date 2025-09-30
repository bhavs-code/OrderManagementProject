package com.mywork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mywork.dto.ProductRequestDto;
import com.mywork.dto.ProductResponseDto;
import com.mywork.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

//	@Autowired
	private final ProductService productService;
	
	//construction injection
	public ProductController(ProductService productService) {

		this.productService = productService;
	}


	//sending in payload is known as RequestBody
	@PostMapping("/save")
	public ProductResponseDto saveProduct(@RequestBody ProductRequestDto productRequestDto) {
		
		return productService.save(productRequestDto);
		
	}
	
	@PostMapping("/save/all")
	public List<ProductResponseDto> saveAll(@RequestBody List<ProductRequestDto> productRequestDtos) {
		return productService.saveAllProducts(productRequestDtos);
	}
	
	@GetMapping
	public List<ProductResponseDto> getProducts() {
		return productService.getProducts();
	}
	
	//sending the value in url through /3 or /6 its' called path variable
	@GetMapping("/{id}")
	public ProductResponseDto getProductById(@PathVariable long id) {
		return productService.getProduct(id);
	}
	
	@GetMapping("/name")
	public List<ProductResponseDto> getProductByName(@RequestParam (name = "productName") String name) {
		return productService.getProductByName(name);
	}
	
	@PutMapping("/update/{id}")
	public ProductResponseDto updateProductRating(@PathVariable long id, @RequestParam double rating) {
		return productService.updateProductByRating(id,rating);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProductById(@PathVariable long id) {
		return productService.deleteProductById(id);
	}
	
}
