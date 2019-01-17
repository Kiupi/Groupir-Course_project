package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
 @Entity
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long stepId;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Getter @Setter
    private Product product;

    @Column(nullable = false)
    @Getter @Setter
    private Integer threshold;
}
