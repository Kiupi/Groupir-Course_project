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
}
