package com.groupir.backend.exceptions;

import com.groupir.backend.dto.ProductDetailsDTO;

import java.util.List;

public class ProductNotFoundException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}
