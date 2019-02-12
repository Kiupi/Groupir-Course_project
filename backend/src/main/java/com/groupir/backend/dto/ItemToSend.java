package com.groupir.backend.dto;

import com.groupir.backend.model.Address;
import com.groupir.backend.model.Order;
import com.groupir.backend.model.ProductOption;
import lombok.Data;

import java.util.Date;

@Data
public class ItemToSend {
    private Address address;
    private Date dispatchmentDate;
    private String trackingNumber;
    private Integer quantity;
    private ProductOption option;
    private Order order;
}
