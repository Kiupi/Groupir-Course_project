package com.groupir.backend.exceptions;

public class ProductOptionNotFoundException extends RuntimeException {
    public ProductOptionNotFoundException(String message) {
        super(message);
    }
}
