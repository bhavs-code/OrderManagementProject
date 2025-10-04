package com.mywork.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mywork.dao.OrderItemRepository;
import com.mywork.dao.OrderRepository;
import com.mywork.dao.ProductRepository;
import com.mywork.dto.OrderItemResponseDto;
import com.mywork.dto.OrderRequestDto;
import com.mywork.dto.OrderResponseDto;
import com.mywork.exception.OrderItemNotFoundException;
import com.mywork.exception.OrderNotFoundException;
import com.mywork.model.Order;
import com.mywork.model.OrderItem;
import com.mywork.model.Product;
import com.mywork.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Override
	public OrderResponseDto placeOrder(List<OrderRequestDto> orderRequestDtos) {
		LOGGER.trace("orderRequestDtos information {}", orderRequestDtos);
		LOGGER.debug("Entered plaveOreder method");
		Order order = new Order();
		order.setStatus("Ordered");
		
		List<OrderItem> orderItemList = new ArrayList<>();
		
		for(OrderRequestDto orderRequestDto: orderRequestDtos) {
			OrderItem orderItem = new OrderItem();
			Product product = productRepository.findById(orderRequestDto.getProductId())
					.orElseThrow(() -> {
						LOGGER.error("N found any product id: {}", orderRequestDto.getProductId());
					return new RuntimeException("Id not Found");
					});
			if(product.getStock() >= orderRequestDto.getQuantity()) {
				orderItem.setQuantity(orderRequestDto.getQuantity());
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				orderItemList.add(orderItem);
				productRepository.updateStock(product.getProductId(), product.getStock()-orderRequestDto.getQuantity());
			} else {
				LOGGER.warn("Insufficieant stock {}", product.getStock());
			}
		}
		
		order.setOrderItems(orderItemList);
		Order savedOrder = orderRepository.save(order);
		
		OrderResponseDto orderResponseDto = buildOrderResponseDto(savedOrder);
		return orderResponseDto;
	}

	@Override
	public ResponseEntity<OrderResponseDto> getOrderInfo(long orderId) {
		Order order = orderRepository.findById(orderId)
						.orElseThrow(() -> new OrderNotFoundException("No Order found with id: " +orderId));
		
		OrderResponseDto orderResponseDto = buildOrderResponseDto(order);
		return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
	}
	
	@Override
	public ResponseEntity<Void> cancelOrderItem(long orderItemId) {
		OrderItem orderItem = orderItemRepository.findById(orderItemId)
							.orElseThrow(() -> new OrderItemNotFoundException("Order Item not found with id: "+orderItemId));
		orderItemRepository.delete(orderItem);
		long productId = orderItem.getProduct().getProductId();
		int stock = orderItem.getProduct().getStock();
		productRepository.updateStock(productId, stock+orderItem.getQuantity());
		
		return ResponseEntity.noContent().build();
		//both are applicable
//		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	private OrderResponseDto buildOrderResponseDto(Order savedOrder) {
		OrderResponseDto orderResponseDto = new OrderResponseDto();
		
		orderResponseDto.setOrderId(savedOrder.getOrderId());
		orderResponseDto.setStatus(savedOrder.getStatus());
		
		List<OrderItemResponseDto> orderItemResponseDtosList = new ArrayList<OrderItemResponseDto>();
		double totalOrderAmount= 0;
		
		for (OrderItem orderItem: savedOrder.getOrderItems() ) {
			OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
			orderItemResponseDto.setProductId(orderItem.getProduct().getProductId());
			orderItemResponseDto.setProductName(orderItem.getProduct().getProductName());
			orderItemResponseDto.setQuantity(orderItem.getQuantity());
			orderItemResponseDto.setEachProductPrice(orderItem.getProduct().getPrice());
			double eachProductPrice = orderItem.getProduct().getPrice() * ((100-orderItem.getProduct().getDiscount()) / 100);
			orderItemResponseDto.setTotalProductPrice(eachProductPrice);
			totalOrderAmount += eachProductPrice;
			orderItemResponseDtosList.add(orderItemResponseDto);
			
		}
		
		orderResponseDto.setTotalAmount(totalOrderAmount);
		orderResponseDto.setOrderItems(orderItemResponseDtosList);
		return orderResponseDto;
	}

	

}
