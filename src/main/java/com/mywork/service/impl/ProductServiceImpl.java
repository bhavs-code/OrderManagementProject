package com.mywork.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mywork.dao.ProductRepository;
import com.mywork.dto.ProductRequestDto;
import com.mywork.dto.ProductResponseDto;
import com.mywork.model.Product;
import com.mywork.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public ProductResponseDto save(ProductRequestDto productRequestDto) {
		
		Product product = new Product();
		product.setProductName(productRequestDto.getProductName());
		product.setPrice(productRequestDto.getPrice());
		product.setDiscount(productRequestDto.getDiscount());
		product.setStock(productRequestDto.getStock());
		if(product.getStock()>0) {
			product.setAvailable(true);
		}
		
		
		Product savedItem = productRepository.save(product);
		
		ProductResponseDto productResponseDto = new ProductResponseDto();
		BeanUtils.copyProperties(savedItem, productResponseDto);
		
		
		return productResponseDto;
	}

	@Override
	public List<ProductResponseDto> getProducts() {
		List<Product> products = productRepository.findAll();
		
		List<ProductResponseDto> productsList = buildProductResponseDtoList(products);	
		
		return productsList;
	}

	@Override
	public ProductResponseDto getProduct(long id) {
		Optional<Product> byId = productRepository.findById(id);
		Product product = byId.get();
		ProductResponseDto dto = new ProductResponseDto();
		BeanUtils.copyProperties(product, dto);
		
		return dto;
	}

	@Override
	public List<ProductResponseDto> getProductByName(String productName) {
		// TODO Auto-generated method stub
		List<Product> products = productRepository.findByProductNameContaining(productName);
		List<ProductResponseDto> productResponseDtoList = buildProductResponseDtoList(products);
		return productResponseDtoList;
	}
	
	@Override
	public List<ProductResponseDto> saveAllProducts(List<ProductRequestDto> productRequestDtos) {
		
		 List<Product> products = buildProductsList(productRequestDtos);
		 
		 List<Product> saveAll = productRepository.saveAll(products);
		 
		 List<ProductResponseDto> productResponseDtoList = buildProductResponseDtoList(saveAll);
		 return productResponseDtoList;
	}

	@Override
	public ProductResponseDto updateProductByRating(long id, double rating) {
		Optional<Product>  optionalProduct= productRepository.findById(id);
		
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setRating(rating);
		
			Product savedProduct = productRepository.save(product);
			ProductResponseDto productResponseDto = new ProductResponseDto();
			BeanUtils.copyProperties(savedProduct, productResponseDto);
			return productResponseDto ;
		}
		return new ProductResponseDto();
	}
	
	@Override
	public String deleteProductById(long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			productRepository.delete(product);
			return product.getProductName();
		}
		return null;
	}

	private List<Product> buildProductsList(List<ProductRequestDto> productRequestDtos) {
		List<Product> products = new ArrayList<>();
		 
		 for(ProductRequestDto productRequestDto:productRequestDtos) {
			 Product product = new Product();
			 BeanUtils.copyProperties(productRequestDto, product);
			 product.setAvailable(true);
			 products.add(product);
		 }
		return products;
	}
	
	private List<ProductResponseDto> buildProductResponseDtoList(List<Product> products) {
		List<ProductResponseDto> productsList = new ArrayList<ProductResponseDto>();
		for (Product product:products) {
			ProductResponseDto productResponseDto = new ProductResponseDto();
			BeanUtils.copyProperties(product, productResponseDto);
			productsList.add(productResponseDto);
			
		}
		return productsList;
	}

}
