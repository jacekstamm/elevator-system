package com.system.elevator.exception;

public class WrongElevatorIdException extends RuntimeException {
    public WrongElevatorIdException(int elevatorID) {
        super("Wrong Elevator ID selected : " + elevatorID);
    }
}
