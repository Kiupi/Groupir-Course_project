package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long productId;

    @ManyToOne
    @Getter @Setter
    private User manufacturer;

    @ManyToOne
    @Getter @Setter
    private Category category;

    @Column
    @Getter @Setter
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    @Getter @Setter
    private String description;

    @Column
    @Getter @Setter
    private Date endDate;

    @Column
    @Getter @Setter
    private Long maxSales;
}
