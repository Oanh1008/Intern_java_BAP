package com.backend.exception;

public class MinSizeMustBeLessThanMaxSize extends Exception{
    public MinSizeMustBeLessThanMaxSize(String message){
        super(message);
    }
}
