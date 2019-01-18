package com.groupir.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private Date birthDate;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(nullable = false)
    private Role role;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    private Address defaultAddress;

    @Column(nullable = false)
    private String password;
}
