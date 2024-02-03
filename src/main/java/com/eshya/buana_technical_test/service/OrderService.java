package com.eshya.buana_technical_test.service;

import com.eshya.buana_technical_test.model.Order;
import com.eshya.buana_technical_test.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    //find all order by customer name
    public List<Order> findAllByCustomerName(String customerName) {
        return orderRepository.findAllByCustomerName(customerName);
    }

    // find order by customer name and id
    public Optional<Order> findByCustomerNameAndId(String customerName, UUID id) {
        return orderRepository.findByCustomerNameAndId(customerName, id);
    }

    //save order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }
}
