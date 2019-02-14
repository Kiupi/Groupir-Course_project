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

    public OrderItemKey(Long orderId, Long optionId){
        order = new Order(orderId, null, null, null, null);
        option = new ProductOption(optionId, null, null, null, null);
    }

    @ManyToOne
    public ProductOption option;
    @ManyToOne
    public Order order;
}
