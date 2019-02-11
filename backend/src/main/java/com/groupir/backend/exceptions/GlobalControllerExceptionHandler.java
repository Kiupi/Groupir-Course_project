package com.groupir.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "User not found") // 400
    @ExceptionHandler(UserNotFoundException.class)
    public void UserConflict() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Category not found") // 400
    @ExceptionHandler(CategoryNotFoundException.class)
    public void CategoryConflict() {
        // Nothing to do
    }
}
