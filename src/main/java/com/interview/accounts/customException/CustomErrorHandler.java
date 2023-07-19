package com.interview.accounts.customException;


import com.interview.accounts.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomErrorHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomError> handleConstraintViolationException(ConstraintViolationException exception) {
        CustomError customError = new CustomError();
        customError.setStatus(HttpStatus.BAD_REQUEST);
        customError.setMessage(exception.getMessage());
       // customError.setConstraintViolations(exception.getConstraintViolations());
        return ResponseEntity.badRequest().body(customError);
    }
}
