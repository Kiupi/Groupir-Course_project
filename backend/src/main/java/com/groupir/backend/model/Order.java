package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long ordereId;

    @Column
    @Getter @Setter
    private Date orderDate;

    @ManyToOne
    @Getter @Setter
    private User user;

    @ManyToOne
    @Getter @Setter
    private PaymentMethod paymentMethod;
}
