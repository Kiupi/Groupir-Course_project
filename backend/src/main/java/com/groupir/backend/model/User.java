package com.groupir.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Integer userId;

    @Column
    @Getter @Setter
    private String lastName;

    @Column
    @Getter @Setter
    private String firstName;

    @Column
    @Getter @Setter
    private Date birthDate;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(nullable = false)
    @Getter @Setter
    private Role role;

    @Column(nullable = false, unique = true)
    @Getter @Setter
    private String email;

    @ManyToOne
    @Getter @Setter
    private Address defaultAddress;

    @Column(nullable = false)
    @Getter @Setter
    private String password;
}
