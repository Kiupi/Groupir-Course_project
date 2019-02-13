package com.groupir.backend.controller;

import com.groupir.backend.dto.ItemToSend;
import com.groupir.backend.dto.ItemToSendUpdate;
import com.groupir.backend.exceptions.OrderItemNotFoundException;
import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.User;
import com.groupir.backend.service.ServiceSupplier;
import com.groupir.backend.service.ServiceUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/supplier")
public class SupplierRestController {

    private ServiceUser serviceUser;
    private ServiceSupplier serviceSupplier;

    @Autowired
    public SupplierRestController(ServiceUser serviceUser, ServiceSupplier serviceSupplier) {
        this.serviceUser = serviceUser;
        this.serviceSupplier = serviceSupplier;
    }

    /**
     * retireve the list of items managed by the euthenticated manufacturer
     *
     * @param authentication [Injected] the authentication object
     * @return a JSON containing the list of orders
     */
    @GetMapping("/items")
    public List<ItemToSend> getItemsToSend(Authentication authentication) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(OrderItem.class, ItemToSend.class)
                .addMappings(mapping -> {
                    mapping.map(source -> source.getKey().getOrder().getAddress(), ItemToSend::setOrderAddress);
                    mapping.map(source -> source.getKey().getOrder().getOrderId(), ItemToSend::setOrderId);
                    mapping.map(OrderItem::getDispatchmentDate, ItemToSend::setDispatchmentDate);
                    mapping.map(OrderItem::getTrackingNumber, ItemToSend::setTrackingNumber);
                    mapping.map(OrderItem::getQuantity, ItemToSend::setQuantity);

                    mapping.map(source -> source.getKey().getOption().getManufaturerReference(), (destination, value) -> destination.getOption().setManufacturerReference((String) value));
                    mapping.map(source -> source.getKey().getOption().getOptionId(), (destination, value) -> destination.getOption().setOptionId((Long) value));
                    mapping.map(source -> source.getKey().getOption().getOptionName(), (destination, value) -> destination.getOption().setOptionName((String) value));
                    mapping.map(source -> source.getKey().getOption().getProduct().getProductId(), (destination, value) -> destination.getOption().setProductId((Long) value));
                    mapping.map(source -> source.getKey().getOption().getProduct().getName(), (destination, value) -> destination.getOption().setProductName((String) value));
                });

        User user = serviceUser.findAuthenticated(authentication);
        List<ItemToSend> dbg = serviceSupplier.retrieveItemsToSend(user).stream()
                .map(orderItem -> modelMapper.map(orderItem, ItemToSend.class))
                .collect(Collectors.toList());
        return dbg;
    }

    @PostMapping("/items/{orderId}/{optionId}")
    public ItemToSendUpdate updateDeliveryStatus(@PathVariable Long optionId, @PathVariable Long orderId, @RequestBody ItemToSendUpdate itemToSendUpdate) {
        if (itemToSendUpdate.getOptionId().equals(optionId) &&
                itemToSendUpdate.getOrderId().equals(orderId)) {
            serviceSupplier.updateDeliveryStatus(itemToSendUpdate);
            return itemToSendUpdate;
        } else {
            throw new OrderItemNotFoundException("The OrderItem in url and in the doesn't match");
        }
    }
}
