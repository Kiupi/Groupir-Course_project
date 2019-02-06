package com.groupir.backend.service;

import com.google.common.collect.Lists;
import com.groupir.backend.model.Order;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUserOrders {

    @Autowired
    private OrderRepository orderRepository;

    /**
     *  find all order of user
     * @param user
     * @return Orders's list
     */
    public List<Order> getListOrderByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
