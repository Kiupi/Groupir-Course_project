package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long paymentMethodId;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Getter @Setter
    private User user;

    @Column(nullable = false)
    @Getter @Setter
    private String type;

    @Column(nullable = false)
    @Getter @Setter
    private String token;
}
