package com.backend.exception;

public class DoubleUniqueRoomCode extends RuntimeException{
    public DoubleUniqueRoomCode(String massage){
        super(massage);

    }
}
