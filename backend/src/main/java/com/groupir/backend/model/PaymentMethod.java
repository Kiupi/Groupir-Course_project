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
    @Getter @Setter
    private User user;

    @Column
    @Getter @Setter
    private String type;

    @Column
    @Getter @Setter
    private String token;
}
