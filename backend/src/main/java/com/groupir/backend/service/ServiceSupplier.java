package com.groupir.backend.service;

import com.groupir.backend.dto.ItemToSend;
import com.groupir.backend.dto.UpdatedDelivery;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ServiceSupplier {
    private OrderItemRepository orderItemRepository;

    @Autowired
    public ServiceSupplier(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public List<ItemToSend> retrieveItemsToSend(User user){
        return null;
    }

    public ItemToSend updateDeliveryStatus(UpdatedDelivery updatedDelivery){
        return null;
    }
}
