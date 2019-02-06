package com.groupir.backend.repository;

import com.groupir.backend.model.Order;
import com.groupir.backend.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByUser(User user);
}
