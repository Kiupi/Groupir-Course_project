package com.groupir.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor @NoArgsConstructor
public class StepWithPrice {
    private BigDecimal price;
    private Long stepId;
    private Integer threshold;
}
