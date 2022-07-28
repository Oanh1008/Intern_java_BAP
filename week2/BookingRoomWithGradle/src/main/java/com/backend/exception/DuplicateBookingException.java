package com.backend.exception;

public class DuplicateBookingException extends RuntimeException{
    /**
     * throw DuplicateBookingException if startTime and endTime in BookingDTO
     * in range startTime and endTime which existed with the same roomCode
     * @param message
     */
    public DuplicateBookingException(String message){
        super(message);
    }

}
