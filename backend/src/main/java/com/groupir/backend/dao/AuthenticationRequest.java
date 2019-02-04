package com.groupir.backend.dao;

import lombok.Data;

@Data
public class AuthenticationRequest {
    String email;
    String password;
}
