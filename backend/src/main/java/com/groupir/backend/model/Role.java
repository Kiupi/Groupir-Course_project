package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Role {

    public static String ADMIN = "ADMIN";
    public static String USER = "USER";
    public static String SUPPLIER = "SUPPLIER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    @Column(nullable = false)
    private String roleName;
}
