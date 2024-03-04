package com.example.calendarapp.exception;

public class InvalidEventException extends RuntimeException{
    public InvalidEventException(String messge){
        super(messge);
    }
}
