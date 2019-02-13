package com.groupir.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ItemToSendUpdate {
    private Long orderId;
    private Long optionId;
    private Date dispatchmentDate;
    private String trackingNumber;
}
