package com.system.elevator.exception;

public class InvalidInitialRequestException extends RuntimeException {
    public InvalidInitialRequestException() {
        super("Elevator or number of floors must be greater that 0!");
    }
}
