package com.groupir.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItemKey implements Serializable {
    @ManyToOne
    public ProductOption option;
    @ManyToOne
    public Order order;
}
