package com.groupir.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
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
