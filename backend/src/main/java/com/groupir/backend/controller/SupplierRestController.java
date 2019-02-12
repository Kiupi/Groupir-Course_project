package com.groupir.backend.controller;

import com.groupir.backend.dto.UpdatedDelivery;
import com.groupir.backend.model.Order;
import com.groupir.backend.model.OrderItem;
import com.groupir.backend.model.ProductOption;
import com.groupir.backend.model.User;
import com.groupir.backend.service.ServiceSupplier;
import com.groupir.backend.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@CrossOrigin
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
     * @param authentication [Injected] the authentication object
     * @return a JSON containing the list of orders
     */
    @GetMapping("/items")
    public List<OrderItem> getItemsToSend(Authentication authentication){
        User user = serviceUser.findAuthenticated(authentication);
        return serviceSupplier.retrieveItemsToSend(user);
    }

    @PostMapping("/items/{option}/{order}")
    public OrderItem updateDeliveryStatus(@PathVariable ProductOption option, @PathVariable Order order, @RequestBody UpdatedDelivery updatedDelivery){
        if(updatedDelivery.getOption().getOptionId().equals(option.getOptionId()) &&
        updatedDelivery.getOrder().getOrderId().equals(order.getOrderId())){
            return serviceSupplier.updateDeliveryStatus(updatedDelivery);
        }
        else {
            return null;
        }
    }
}
