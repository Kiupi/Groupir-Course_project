package com.groupir.backend.dto;

import lombok.Data;

@Data
public class ProductOptionForItemToSend {
    private String manufacturerReference;
    private Long optionId;
    private String optionName;
    private Long productId;
    private String productName;
}
