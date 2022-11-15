package com.system.elevator.model;

import com.system.elevator.enums.ElevatorDirection;
import com.system.elevator.enums.ElevatorStatus;
import java.util.LinkedList;
import java.util.Queue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Elevator {

    private int id;
    private Integer currentFloor;
    private Queue<Integer> destinationFloors;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.destinationFloors = new LinkedList<>();
    }

    public void moveUp() {
        currentFloor++;
    }

    public void moveDown() {
        currentFloor--;
    }

    public void popDestination() {
        this.destinationFloors.remove();
    }

    public void addDestination(Integer destinationFloor) {
        this.destinationFloors.add(destinationFloor);
        log.info("Added new destination for elevator with ID: " + this.id + ". New destination is " + destinationFloor + " floor.");
    }

    public ElevatorDirection direction() {
        if (destinationFloors.size() > 0) {
            if (currentFloor < destinationFloors.peek()) {
                return ElevatorDirection.UP;
            } else if (currentFloor > destinationFloors.peek()) {
                return ElevatorDirection.DOWN;
            }
        }
        return ElevatorDirection.HOLD;
    }

    public ElevatorStatus status() {
        return  (destinationFloors.size() > 0) ? ElevatorStatus.OCCUPIED : ElevatorStatus.EMPTY;
    }
}
