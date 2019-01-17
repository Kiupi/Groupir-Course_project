package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderItem {
    @EmbeddedId
    @Getter @Setter
    private OrderItemKey key;

    @Column
    @Getter @Setter
    private Date dispatchmentDate;

    @Column
    @Getter @Setter
    private String trackingNumber;

    @Column
    @Getter @Setter
    private Integer quantity;
}
