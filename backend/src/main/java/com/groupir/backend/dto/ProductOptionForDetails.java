package com.groupir.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionForDetails {
    private Long optionId;
    private String optionName;
    private String manufaturerReference;
    private String image;
    private List<StepWithPrice> steps;
}
