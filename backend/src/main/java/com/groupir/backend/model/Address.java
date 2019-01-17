package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long addressId;

    @Column
    @Getter @Setter
    private String number;

    @Column(nullable = false)
    @Getter @Setter
    private String street;

    @Column(nullable = false)
    @Getter @Setter
    private String city;

    @Column(nullable = false)
    @Getter @Setter
    private String postalCode;

    @Column(nullable = false)
    @Getter @Setter
    private String country;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Getter @Setter
    private User user;
}
