package com.groupir.backend.dto;

import com.groupir.backend.model.Category;
import com.groupir.backend.model.ProductOption;
import com.groupir.backend.model.Step;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class ProductDetailsDTO {
    private Long productId;
    private Category category;
    private String name;
    private String description;
    private Date endDate;
    private Long maxSales;
    private List<ProductOptionForDetails> options;
}
