package com.mywork.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mywork.dto.OrderRequestDto;
import com.mywork.dto.OrderResponseDto;

public interface OrderService {
	
	public OrderResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos);

	public ResponseEntity<OrderResponseDto> getOrderInfo(long orderId);

	public ResponseEntity<Void> cancelOrderItem(long orderItemId);

}
