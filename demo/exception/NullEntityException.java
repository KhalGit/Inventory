package com.gkalogeroudis.springexercise.demo.exception;

public class NullEntityException extends RuntimeException   {

    public NullEntityException(Object object)   {
        super(object.toString() + " is empty.");
    }
}
