package com.gkalogeroudis.springexercise.demo.exception;

public class NegativeValueException extends RuntimeException{

    public NegativeValueException(String priceOrQuantity) {
        super(priceOrQuantity + " is negative.");
    }

}
