package com.groupir.backend.dto;

import com.groupir.backend.model.Address;
import lombok.Data;

import java.util.Date;

@Data
public class ItemToSend {

    private ProductOptionForItemToSend option = new ProductOptionForItemToSend();
    private Address orderAddress;
    private Long orderId;
    private Date dispatchmentDate;
    private String trackingNumber;
    private Integer quantity;
}
