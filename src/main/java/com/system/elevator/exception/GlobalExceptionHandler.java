package com.system.elevator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(InvalidInitialRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidInitialRequestHandler(InvalidInitialRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongElevatorIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String wrongElevatorIdHandler(WrongElevatorIdException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(WrongUpdateRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String wrongUpdateRequestHandler(WrongUpdateRequestException exception) {
        return exception.getMessage();
    }
}
