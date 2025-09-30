package com.mywork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
	
	private String productName;
	
	private double price;
	
	private double discount;
	
	private double rating;
	
	private int stock;

}
