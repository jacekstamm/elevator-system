package com.system.elevator.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DestinationRequest {

    private Integer elevatorId;
    private Integer destinationFloor;
}
