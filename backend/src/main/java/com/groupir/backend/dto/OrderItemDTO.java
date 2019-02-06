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
    private long optionId;
    private String  optionName;
    private String image;
    private long productId;
    private String productName;
    private BigDecimal price ;
}
