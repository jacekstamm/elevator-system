package com.system.elevator.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InitializeRequest {

    private int numberOfElevators;
    private int numberOfFloors;
}
