package com.groupir.backend.service;

import com.groupir.backend.dto.ItemToSendUpdate;
import com.groupir.backend.exceptions.OperationNotAllowedException;
import com.groupir.backend.exceptions.OrderItemNotFoundException;
import com.groupir.backend.exceptions.UserNotFoundException;
import com.groupir.backend.model.*;
import com.groupir.backend.repository.OrderItemRepository;
import com.groupir.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ServiceSupplier {
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;

    @Autowired
    public ServiceSupplier(OrderItemRepository orderItemRepository, UserRepository userRepository) {
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    /**
     * retrieve the list of order items and filter it to keep only the items sent or to be sent by the manufacturer specified
     *
     * @param manufacturer the User object corresponding to the manufacturer
     * @return the list of OrderITems mangaed by the manufacturer
     */
    public List<OrderItem> retrieveItemsToSend(User manufacturer) {
        return StreamSupport.stream(orderItemRepository.findAll().spliterator(), true)
                .filter(orderItem -> orderItem.getKey().option.getProduct().getManufacturer().getUserId().equals(manufacturer.getUserId()))
                .filter(orderItem -> orderItem.getKey().option.getProduct().getEndDate().before(new Date()))
                .collect(Collectors.toList());
    }

    /**
     * update delivery information of an OrderItem using informations specified by the manufacturer
     *
     * @param itemToSendUpdate the object containing the Id of the OrderItem and the new informations for this object
     * @return the updated OrderItem
     */
    public OrderItem updateDeliveryStatus(ItemToSendUpdate itemToSendUpdate, Authentication authentication) {

        Optional<User> oManufacturer = userRepository.findByEmail(authentication.getName());
        if (!oManufacturer.isPresent()) {
            throw new UserNotFoundException("Could not find the User corresponding to the one authenticated");
        }

        OrderItemKey oiKey = new OrderItemKey(new ProductOption(), new Order());
        oiKey.option.setOptionId(itemToSendUpdate.getOptionId());
        oiKey.order.setOrderId(itemToSendUpdate.getOrderId());
        Optional<OrderItem> oItem = orderItemRepository.findById(oiKey);

        if (!oItem.isPresent()) {
            throw new OrderItemNotFoundException("The OrderItem updated by supplier could not be find");
        }

        if (oItem.get().getKey().getOption().getProduct().getManufacturer().getUserId()
                .equals(oManufacturer.get().getUserId())) {
            OrderItem orderItem = oItem.get();
            orderItem.setTrackingNumber(itemToSendUpdate.getTrackingNumber());
            orderItem.setDispatchmentDate(itemToSendUpdate.getDispatchmentDate());
            return orderItemRepository.save(orderItem);
        }
        else {
            throw new OperationNotAllowedException("The OrderItem you're trying to update is not yours");
        }
    }
}
