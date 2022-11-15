package com.system.elevator.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateRequest {

    private int elevatorId;
    private Integer oldDestination;
    private Integer newDestination;
}
