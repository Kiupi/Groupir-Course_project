package com.groupir.backend.dto;

import com.groupir.backend.model.Order;
import com.groupir.backend.model.ProductOption;
import lombok.Data;
import java.util.Date;

@Data
public class UpdatedDelivery {
    private Order order;
    private ProductOption option;
    private Date dispatchmentDate;
    private String trackingNumber;
}
