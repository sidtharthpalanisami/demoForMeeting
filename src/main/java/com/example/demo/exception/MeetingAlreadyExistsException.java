package com.example.demo.exception;

public class MeetingAlreadyExistsException extends Exception{

    public MeetingAlreadyExistsException(String msg)
    {
        super(msg);
    }
}
