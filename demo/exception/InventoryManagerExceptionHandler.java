package com.gkalogeroudis.springexercise.demo.exception;

import com.gkalogeroudis.springexercise.demo.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class InventoryManagerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NegativeValueException exc){
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
