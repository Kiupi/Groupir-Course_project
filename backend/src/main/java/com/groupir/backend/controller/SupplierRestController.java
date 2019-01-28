package com.groupir.backend.controller;

import com.groupir.backend.dto.ItemToSend;
import com.groupir.backend.dto.UpdatedDelivery;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/supplier")
public class SupplierRestController {

    public List<ItemToSend> getItemsToSend(Authentication authentication){
        return null;
    }

    public ItemToSend updateDeliveryStatus(UpdatedDelivery updatedDelivery){
        return null;
    }

}
