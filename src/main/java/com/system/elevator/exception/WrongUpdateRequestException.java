package com.system.elevator.exception;

public class WrongUpdateRequestException extends RuntimeException {
    public WrongUpdateRequestException() {
        super("Wrong Old Destination floor in request!");
    }
}
