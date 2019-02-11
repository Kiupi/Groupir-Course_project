package com.groupir.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User manufacturer;

    @ManyToOne
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    private Date endDate;

    @Column
    private Long maxSales;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "product")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Step> steps = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "product")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ProductOption> productOptions = new ArrayList<>();
}
