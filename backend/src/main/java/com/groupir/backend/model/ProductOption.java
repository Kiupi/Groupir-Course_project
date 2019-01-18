package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Column(nullable = false)
    private String optionName;

    @Column
    private String manufaturerReference;

    @Column
    private String image;
}
