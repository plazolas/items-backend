package com.oz.demojar.service;

import com.oz.demojar.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> getAllOrders();

    Optional<Order> getOrderById(Long id);

    void addOrder(String message);
}
