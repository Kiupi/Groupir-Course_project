package com.groupir.backend.dto;

import com.groupir.backend.model.Address;
import com.groupir.backend.model.ProductOption;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private long id;
    private Date dateOrder;
    private Address address;
    private List<OrderItemDTO> orderItems;


}
