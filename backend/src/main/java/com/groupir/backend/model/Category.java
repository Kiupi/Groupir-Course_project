package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer categoryId;

    @Column(nullable = false)
    @Getter @Setter
    private String name;
}
