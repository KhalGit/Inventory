package com.gkalogeroudis.springexercise.demo.exception;

public class NotFoundIdException extends RuntimeException   {

    public NotFoundIdException(Long id) {
        super(id + " is not found.");
    }
}
