package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Price {

    @EmbeddedId
    @Getter @Setter
    private PriceKey key;

    @Column
    @Getter @Setter
    private BigDecimal price;
}
