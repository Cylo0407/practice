package com.example.springbootinit.Exception;

public class ValidException extends RuntimeException{
    public ValidException() {
        super();
    }

    public ValidException(String msg) {
        super(msg);
    }
}
