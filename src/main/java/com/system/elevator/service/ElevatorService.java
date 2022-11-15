package com.system.elevator.service;

import com.system.elevator.exception.InvalidInitialRequestException;
import com.system.elevator.exception.WrongElevatorIdException;
import com.system.elevator.exception.WrongUpdateRequestException;
import com.system.elevator.model.Elevator;
import com.system.elevator.request.DestinationRequest;
import com.system.elevator.request.InitializeRequest;
import com.system.elevator.request.PickUpRequest;
import com.system.elevator.request.UpdateRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElevatorService {

    private int numberOfFloors;
    private ArrayList<Elevator> elevators;
    private Queue<Integer> pickUpLocations;

    public String initializeElevators(InitializeRequest request) {
        final int maxElevators = 16;
        final int maxFloors = 30;
        checkInitialRequest(request);
        int numberOfElevators = Math.min(request.getNumberOfElevators(), maxElevators);
        this.numberOfFloors = Math.min(request.getNumberOfFloors(), maxFloors);
        elevators = new ArrayList<>();
        initElevators(numberOfElevators);
        pickUpLocations = new LinkedList<>();
        log.info(initializeReturnString(elevators.size()));
        return initializeReturnString(elevators.size());
    }

    public void pickUp(PickUpRequest request) {
        checkElevatorID(request.getElevatorId());
        Elevator selectedElevator = elevators.get(request.getElevatorId());
        selectedElevator.addDestination(request.getPickUpFloor());
    }

    public String destination(DestinationRequest request) {
        checkElevatorID(request.getElevatorId());
        elevators.get(request.getElevatorId()).addDestination(request.getDestinationFloor());
        log.info(destinationReturnString(request.getElevatorId(), request.getDestinationFloor()));
        return destinationReturnString(request.getElevatorId(), request.getDestinationFloor());
    }

    public String update(UpdateRequest request) {
        checkElevatorID(request.getElevatorId());
        Elevator elevatorToUpdate = elevators.get(request.getElevatorId());
        checkUpdateRequest(elevatorToUpdate, request.getOldDestination());
        Integer elevateFloor = elevatorToUpdate.getDestinationFloors().peek();
        Queue<Integer> queue = elevatorToUpdate.getDestinationFloors();
        queue.remove(elevateFloor);
        if (queue.size() == 0) {
            queue.add(request.getNewDestination());
        } else {
            updateFirstElementInQueue(request, elevatorToUpdate, queue);
        }
        return "Elevator destination Updated : " + elevatorToUpdate.getDestinationFloors().toString();
    }

    public void step() {
        for (Elevator currentElevator : elevators) {
            switch (currentElevator.status()) {
                case EMPTY:
                    if (!pickUpLocations.isEmpty()) {
                        currentElevator.addDestination(pickUpLocations.poll());
                    }
                    break;
                case OCCUPIED:
                    switch (currentElevator.direction()) {
                        case UP:
                            log.info("Elevator with id: " + currentElevator.getId() + " moved from " + currentElevator.getCurrentFloor() +
                              " floor to " + (currentElevator.getCurrentFloor() + 1) + " floor.");
                            currentElevator.moveUp();
                            break;
                        case DOWN:
                            log.info("Elevator with id: " + currentElevator.getId() + " moved from " + currentElevator.getCurrentFloor() +
                              " floor to " + (currentElevator.getCurrentFloor() - 1) + " floor.");
                            currentElevator.moveDown();
                            break;
                        case HOLD:
                            currentElevator.popDestination();
                            break;
                    }
            }
        }
    }

    public String elevatorsStatus() {
        return elevators.toString();
    }

    private void checkInitialRequest(InitializeRequest request) {
        if (request.getNumberOfElevators() <= 0 || request.getNumberOfFloors() <= 0) {
            throw new InvalidInitialRequestException();
        }
    }

    private void initElevators(int numberOfElevators) {
        for (int i = 0; i < numberOfElevators; i++) {
            elevators.add(new Elevator(i));
        }
    }

    private void checkElevatorID(int elevatorId) {
        if (elevatorId < 0 || elevatorId > elevators.size() - 1) {
            throw new WrongElevatorIdException(elevatorId);
        }
    }

    private void checkUpdateRequest(Elevator elevatorToUpdate, Integer requestOldFlood) {
        if (!Objects.requireNonNull(elevatorToUpdate.getDestinationFloors().peek()).equals(requestOldFlood)) {
            throw new WrongUpdateRequestException();
        }
    }

    private void updateFirstElementInQueue(UpdateRequest request, Elevator elevatorToUpdate, Queue<Integer> queue) {
        List<Integer> queueList = new ArrayList<>(queue);
        Queue<Integer> newQueue = new LinkedList<>();
        newQueue.add(request.getNewDestination());
        newQueue.addAll(queueList);
        elevatorToUpdate.setDestinationFloors(newQueue);
    }

    protected List<Elevator> getElevators() {
        return elevators;
    }

    protected int getNumberOfFloors() {
        return numberOfFloors;
    }

    private String initializeReturnString(int elevatorSize) {
        return "System was initialized with " + elevatorSize + " elevators operating over " + this.numberOfFloors + " floors.";
    }

    private String destinationReturnString(int elevationId, int destinationFloor) {
        return "Elevator with id " + elevationId + " get new destination floor: " + destinationFloor;
    }
}
