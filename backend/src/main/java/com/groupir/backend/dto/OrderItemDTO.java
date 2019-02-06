package com.groupir.backend.dto;

import com.groupir.backend.model.ProductOption;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItemDTO {
    private String trackingNumber;
    private Date dispatchmentDate;
    private int quantity;
    private String  optionName;
    private String image;
    private String productName;
    private BigDecimal price ;
}
