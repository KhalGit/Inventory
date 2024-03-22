package com.gkalogeroudis.springexercise.demo.exception;

public class DataNotFound extends RuntimeException  {

    public DataNotFound()   {
        super("Data not found");
    }
}
