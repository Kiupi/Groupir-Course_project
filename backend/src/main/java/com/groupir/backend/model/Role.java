package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer roleId;

    @Column
    @Getter @Setter
    private String roleName;
}
