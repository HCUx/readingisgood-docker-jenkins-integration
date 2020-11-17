package com.readingisgood.backend.service;

import com.readingisgood.backend.entity.Order;
import com.readingisgood.backend.entity.dto.OrderDto;
import com.readingisgood.backend.exception.ResourceNotFoundException;
import com.readingisgood.backend.exception.ValidationException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface OrderService {
    OrderDto getOrderById(Long orderId, Authentication authentication) throws ResourceNotFoundException;

    List<OrderDto> getAllUserOrdersByUserId(Authentication authentication) throws ResourceNotFoundException;

    OrderDto createOrder(Order order, Authentication authentication) throws ResourceNotFoundException, ValidationException;

    OrderDto updateOrderStatus(Long orderId, String status, Authentication authentication) throws ResourceNotFoundException, ValidationException;
}
