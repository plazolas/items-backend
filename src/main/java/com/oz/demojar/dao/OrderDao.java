package com.oz.demojar.dao;

import com.oz.demojar.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order addOrder(String Order);

    List<Order> selectAllOrders();

    Optional<Order> getOrderById(Long id);

    void save(Order order);

}
