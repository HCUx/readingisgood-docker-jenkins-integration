package com.readingisgood.backend.controller;

import com.readingisgood.backend.entity.Order;
import com.readingisgood.backend.entity.dto.OrderDto;
import com.readingisgood.backend.exception.ResourceNotFoundException;
import com.readingisgood.backend.exception.ValidationException;
import com.readingisgood.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable(value = "id") Long orderId, Authentication authentication)
            throws ResourceNotFoundException {
        OrderDto order = orderService.getOrderById(orderId, authentication);
        return ResponseEntity.ok().body(order);
    }


    @GetMapping("/orders")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDto>> getAllUserOrdersByUserId(Authentication authentication) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(this.orderService.getAllUserOrdersByUserId(authentication));
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody Order order, Authentication authentication) throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok().body(this.orderService.createOrder(order, authentication));
    }

    @GetMapping("/orders/{id}/status/{status}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable(value = "id") Long orderId, @PathVariable(value = "status") String status,
                                                      Authentication authentication)
            throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok().body(this.orderService.updateOrderStatus(orderId, status, authentication));
    }
}
