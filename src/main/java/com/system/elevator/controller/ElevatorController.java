package com.system.elevator.controller;

import com.system.elevator.request.DestinationRequest;
import com.system.elevator.request.InitializeRequest;
import com.system.elevator.request.PickUpRequest;
import com.system.elevator.request.UpdateRequest;
import com.system.elevator.service.ElevatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elevator")
@RequiredArgsConstructor
public class ElevatorController {

    private final ElevatorService service;

    @PostMapping("/initializeSystem")
    public String initializeSystem(@RequestBody InitializeRequest initializeRequest) {
        return service.initializeElevators(initializeRequest);
    }

    @PostMapping("/pickUp")
    public void pickUpElevator(@RequestBody PickUpRequest pickUpRequest) {
        service.pickUp(pickUpRequest);
    }

    @PostMapping("/destination")
    public String destination(@RequestBody DestinationRequest destinationRequest) {
        return service.destination(destinationRequest);
    }

    @PutMapping("/update")
    public String updateElevatorTrip(@RequestBody UpdateRequest updateRequest) {
        return service.update(updateRequest);
    }

    @PostMapping("/step")
    public void elevatorsStep() {
        service.step();
    }

    @GetMapping("/status")
    public String status() {
        return service.elevatorsStatus();
    }
}
