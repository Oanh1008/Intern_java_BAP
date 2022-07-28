package com.backend.exception;

public class MinSizeMustBeLessThanMaxSizeException extends Exception{
    /**
     *
     * @param message
     */
    public MinSizeMustBeLessThanMaxSizeException(String message){
        super(message);
    }

}
