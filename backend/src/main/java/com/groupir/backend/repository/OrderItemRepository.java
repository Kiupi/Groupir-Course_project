package com.groupir.backend.repository;

import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.OrderItemKey;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemKey> {

    List<OrderItem> findAllByKey_Order_OrderId(Long key_order_orderId);


}
