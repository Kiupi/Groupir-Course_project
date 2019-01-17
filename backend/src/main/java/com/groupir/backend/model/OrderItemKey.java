package com.groupir.backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class OrderItemKey implements Serializable {
    @ManyToOne
    @Getter @Setter
    public ProductOption option;
    @ManyToOne
    @Getter @Setter
    public Order order;
}
