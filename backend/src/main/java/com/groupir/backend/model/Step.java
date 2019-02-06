package com.groupir.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stepId;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonBackReference
    private Product product;

    @Column(nullable = false)
    private Integer threshold;
}
