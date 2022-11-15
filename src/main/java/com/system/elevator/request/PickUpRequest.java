package com.system.elevator.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PickUpRequest {

    private int elevatorId;
    private int pickUpFloor;
}
