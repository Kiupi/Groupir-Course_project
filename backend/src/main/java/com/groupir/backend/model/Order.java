package com.groupir.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Address address;


}
