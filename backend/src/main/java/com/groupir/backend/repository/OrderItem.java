package com.groupir.backend.repository;

import com.groupir.backend.model.OrderItemKey;
import org.springframework.data.repository.CrudRepository;

public interface OrderItem extends CrudRepository<OrderItem, OrderItemKey> {
}
