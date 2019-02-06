package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class OrderItem {
    @EmbeddedId
    private OrderItemKey key;

    @Column
    private Date dispatchmentDate;

    @Column
    private String trackingNumber;

    @Column(nullable = false)
    private Integer quantity;
}
