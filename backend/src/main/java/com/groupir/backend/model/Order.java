package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "user_orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PaymentMethod paymentMethod;
}
