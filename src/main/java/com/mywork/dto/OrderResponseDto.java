package com.mywork.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
	
	private long OrderId;
	
	private String status;
	
	private double totalAmount;
	
	private List<OrderItemResponseDto> orderItems;

}
