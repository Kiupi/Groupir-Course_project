package com.groupir.backend.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    String email;
    String password;
}
