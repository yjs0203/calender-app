package com.example.calenderapp.event.exception;

public class InvalidEventException extends RuntimeException{
    public InvalidEventException(String messge){
        super(messge);
    }
}
