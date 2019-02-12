package com.groupir.backend.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String expired_or_invalid_jwt_token) {
        super(expired_or_invalid_jwt_token);
    }
}
