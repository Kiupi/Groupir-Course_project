package com.groupir.backend.service;

import com.groupir.backend.dto.UpdatedDelivery;
import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.OrderItemKey;
import com.groupir.backend.model.User;
import com.groupir.backend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceSupplier {
    private OrderItemRepository orderItemRepository;

    @Autowired
    public ServiceSupplier(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    /**
     * retrieve the list of order items and filter it to keep only the items sent or to be sent by the manufacturer specified
     * @param manufacturer the User object corresponding to the manufacturer
     * @return the list of OrderITems mangaed by the manufacturer
     */
    public List<OrderItem> retrieveItemsToSend(User manufacturer){
        return StreamSupport.stream(orderItemRepository.findAll().spliterator(), true)
                .filter(orderItem -> orderItem.getKey().option.getProduct().getManufacturer().getUserId().equals(manufacturer.getUserId()))
                .filter(orderItem -> orderItem.getKey().option.getProduct().getEndDate().before(new Date()))
                .collect(Collectors.toList());
    }

    /**
     * update delivery information of an OrderItem using informations specified by the manufacturer
     * @param updatedDelivery the object containing the Id of the OrderItem and the new informations for this object
     * @return the updated OrderItem
     */
    public OrderItem updateDeliveryStatus(UpdatedDelivery updatedDelivery){
        OrderItemKey oiKey = new OrderItemKey();
        oiKey.option = updatedDelivery.getOption();
        oiKey.order = updatedDelivery.getOrder();

        Optional<OrderItem> oItem = orderItemRepository.findById(oiKey);
        if(oItem.isPresent()){
            OrderItem orderItem = oItem.get();
            orderItem.setTrackingNumber(updatedDelivery.getTrackingNumber());
            orderItem.setDispatchmentDate(updatedDelivery.getDispatchmentDate());
            return orderItemRepository.save(orderItem);
        }
        else {
            throw new RuntimeException("The OrderItem updated by supplier could not be find");
        }
    }
}
