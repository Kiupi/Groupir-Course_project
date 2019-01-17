package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class ProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long optionId;

    @ManyToOne
    @Getter @Setter
    private Product product;

    @Column
    @Getter @Setter
    private String optionName;

    @Column
    @Getter @Setter
    private String manufaturerReference;

    @Column
    @Getter @Setter
    private String image;
}
