package com.groupir.backend.repository;

import com.groupir.backend.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
