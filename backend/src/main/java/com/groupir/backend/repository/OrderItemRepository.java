package com.groupir.backend.repository;

import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.OrderItemKey;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemKey> {
}
