package com.oz.demojar.service;

import com.oz.demojar.dao.OrderDao;
import com.oz.demojar.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class OrderServiceImpl implements OrderService { //  throws InvalidDataAccessApiUsageException, NoSuchElementFoundException

    @Autowired
    private OrderDao orderDao;

    public List<Order> getAllOrders() {
        return orderDao.selectAllOrders();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderDao.getOrderById(id);
    }

    public void addOrder(String message) {
        orderDao.save(Order.builder()
                .order(message)
                .build());
    }
}
