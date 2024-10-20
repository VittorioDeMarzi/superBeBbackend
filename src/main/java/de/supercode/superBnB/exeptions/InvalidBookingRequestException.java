package de.supercode.superBnB.exeptions;

public class InvalidBookingRequestException extends RuntimeException{
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
