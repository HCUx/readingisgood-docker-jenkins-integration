package com.readingisgood.backend.service.Impl;

import com.readingisgood.backend.entity.Book;
import com.readingisgood.backend.entity.Order;
import com.readingisgood.backend.entity.OrderItem;
import com.readingisgood.backend.entity.User;
import com.readingisgood.backend.entity.dto.OrderDto;
import com.readingisgood.backend.entity.enums.EPaymentMethod;
import com.readingisgood.backend.entity.enums.EStatus;
import com.readingisgood.backend.exception.ResourceNotFoundException;
import com.readingisgood.backend.exception.ValidationException;
import com.readingisgood.backend.repository.BookRepository;
import com.readingisgood.backend.repository.OrderRepository;
import com.readingisgood.backend.service.OrderService;
import com.readingisgood.backend.springjwt.services.UserDetailsImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public OrderDto getOrderById(Long orderId, Authentication authentication)
            throws ResourceNotFoundException {
        Order order = orderRepository.findByIdAndUserId(orderId, ((UserDetailsImpl) authentication.getPrincipal()).getId());
        if (order == null) {
            throw new ResourceNotFoundException("Order not found for this id: " + orderId);
        }
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllUserOrdersByUserId(Authentication authentication) throws ResourceNotFoundException {
        List<Order> orderList = orderRepository.findAllByUserId(((UserDetailsImpl) authentication.getPrincipal()).getId());
        List<OrderDto> orderDtoList = orderList.stream().map(source -> modelMapper.map(source, OrderDto.class)).collect(Collectors.toList());
        return orderDtoList;
    }

    @Override
    public OrderDto createOrder(Order order, Authentication authentication) throws ResourceNotFoundException, ValidationException {
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        if (order.getPaymentMethod() == null) {
            throw new ResourceNotFoundException("Payment Method not found");
        } else {
            switch (order.getPaymentMethod().name()) {
                case "CREDIT_CARD":
                    order.setPaymentMethod(EPaymentMethod.CREDIT_CARD);
                    break;
                case "MULTINET":
                    order.setPaymentMethod(EPaymentMethod.MULTINET);
                    break;
                case "PAYATDOOR":
                    order.setPaymentMethod(EPaymentMethod.PAYATDOOR);
                    break;
                default:
                    throw new ValidationException("Payment Method format is invalid");
            }
        }

        for (OrderItem orderItem : order.getOrderItems()) {

            Book book = bookRepository.findById(orderItem.getBook().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found for this id: " + orderItem.getBook().getId()));
            if (book.getStock() > 0 && book.getStock() >= orderItem.getQty())
                book.setStock(book.getStock() - orderItem.getQty());
            else
                throw new ResourceNotFoundException("Stock is not enough for this book id:" + book.getId());
            orderItem.setOrder(order);
            orderItem.setBook(book);
        }
        order.setTotalPrice();
        orderRepository.save(order);
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, String status, Authentication authentication) throws ResourceNotFoundException, ValidationException {

        Order order = orderRepository.findByIdAndUserId(orderId, ((UserDetailsImpl) authentication.getPrincipal()).getId());
        if (order == null) {
            throw new ResourceNotFoundException("Order not found for this id: " + orderId);
        }
        if (status != null) {
            switch (status) {
                case "CANCELLED":
                    order.setStatus(EStatus.CANCELLED);
                    break;
                case "WAITING":
                    order.setStatus(EStatus.WAITING);
                    break;
                case "SHIPPED":
                    order.setStatus(EStatus.SHIPPED);
                    break;
                case "DELIVERED":
                    order.setStatus(EStatus.DELIVERED);
                    break;
                default:
                    throw new ValidationException("Status format is invalid");
            }
        } else {
            throw new ResourceNotFoundException("Status not found");
        }
        orderRepository.save(order);
        return modelMapper.map(order, OrderDto.class);
    }


}
