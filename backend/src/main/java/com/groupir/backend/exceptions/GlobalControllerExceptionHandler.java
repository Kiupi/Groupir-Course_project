package com.groupir.backend.exceptions;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class GlobalControllerExceptionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "User not found") // 400
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFound() {
        // Nothing to do
    }

    @ExceptionHandler(NoRoleSetException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The User sent doesn't have a role defined")
    public void handleNoRole(){
        //Nothing to do
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public void handleNotAllowed(final OperationNotAllowedException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), e.getLocalizedMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Category not found") // 400
    @ExceptionHandler(CategoryNotFoundException.class)
    public void CategoryConflict() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.CONFLICT,reason = "this price already exist ") // 409
    @ExceptionHandler(PriceConflictException.class)
    public void PriceConflict() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Step not found") // 400
    @ExceptionHandler(StepNotFoundException.class)
    public void stepNotFound() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "ProductOption not found") // 400
    @ExceptionHandler(ProductOptionNotFoundException.class)
    public void productOptionNotFound() {
        // Nothing to do
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,reason = "Price not found") // 400
    @ExceptionHandler(PriceNotFoundException.class)
    public void priceNotFound() {
        // Nothing to do
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Expired or invalid jwt token")
    public void handleInvalidJwtAuthentication(){
        // Nothing to do
    }
}
