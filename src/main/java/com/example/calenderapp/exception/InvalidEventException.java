package com.example.calenderapp.exception;

public class InvalidEventException extends RuntimeException{
    public InvalidEventException(String messge){
        super(messge);
    }
}
