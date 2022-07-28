package com.backend.exception;

public class DoubleUniqueRoomCodeException extends RuntimeException{ // Exception
    public DoubleUniqueRoomCodeException(String massage){
        super(massage);

    }
}
