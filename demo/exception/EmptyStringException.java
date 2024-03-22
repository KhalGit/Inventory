package com.gkalogeroudis.springexercise.demo.exception;

public class EmptyStringException extends RuntimeException  {

    public EmptyStringException(String stringColumn)    {
        super(stringColumn + " is null.");
    }
}
