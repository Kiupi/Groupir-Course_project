package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Data
public class Price {

    @EmbeddedId
    private PriceKey key;

    @Column(nullable = false)
    private BigDecimal price;
}
