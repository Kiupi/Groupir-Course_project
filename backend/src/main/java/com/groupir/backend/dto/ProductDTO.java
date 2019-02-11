package com.groupir.backend.dto;

import com.groupir.backend.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ProductDTO {

    private long id ;
    private String description;
    private int categoryId;
    private String nameProduct;
    private Date date;
    private String img;
    private int nbOrder;
}
