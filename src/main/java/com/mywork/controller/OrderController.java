package com.mywork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mywork.dto.OrderRequestDto;
import com.mywork.dto.OrderResponseDto;
import com.mywork.service.OrderService;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/buy")
	@Timed("orders.placed.time")
	@Counted("orders.placed.count")
	public OrderResponseDto placeOrder(@RequestBody List<OrderRequestDto> orderRequestDtos) {
		return orderService.placeOrder(orderRequestDtos);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<OrderResponseDto>  getOrderInfo(@PathVariable long orderId) {
		return orderService.getOrderInfo(orderId);
	}
	
	@DeleteMapping("/cancel")
	public ResponseEntity<Void> cancelItem(@RequestParam long orderItemId) {
		return orderService.cancelOrderItem(orderItemId);
	}

}
