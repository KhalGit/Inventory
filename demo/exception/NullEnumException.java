package com.gkalogeroudis.springexercise.demo.exception;

public class NullEnumException extends RuntimeException {

    public NullEnumException(Enum transTypeOrPerson) {
        super(transTypeOrPerson + " is null.");
    }
}
