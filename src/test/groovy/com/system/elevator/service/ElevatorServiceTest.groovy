package com.system.elevator.service

import com.system.elevator.enums.ElevatorDirection
import com.system.elevator.enums.ElevatorStatus
import com.system.elevator.exception.InvalidInitialRequestException
import com.system.elevator.exception.WrongUpdateRequestException
import com.system.elevator.model.Elevator
import com.system.elevator.request.InitializeRequest
import com.system.elevator.request.UpdateRequest
import spock.lang.Specification
import spock.lang.Unroll

import static com.system.elevator.utils.RequestConstants.getDestinationRequest
import static com.system.elevator.utils.RequestConstants.getInitializeRequest
import static com.system.elevator.utils.RequestConstants.getUpdateRequest
import static com.system.elevator.utils.RequestConstants.getWrongUpdateRequest
import static com.system.elevator.utils.RequestConstants.getSecondDestinationRequest

class ElevatorServiceTest extends Specification {

    private ElevatorService service = new ElevatorService()

    def 'should initialize elevator system'() {
        when:
        service.initializeElevators(getInitializeRequest())

        then:
        verifyAll {
            service.getElevators().size() == 3
            service.getNumberOfFloors() == 10
        }
    }

    @Unroll
    def 'should throw InvalidInitialRequestException when request got failed data to initialize system'() {
        given:
        InitializeRequest request = new InitializeRequest(numberOfElevators, numberOfFloors)

        when:
        service.initializeElevators(request)

        then:
        thrown(InvalidInitialRequestException)

        where:
        numberOfElevators || numberOfFloors
        1                 || 0
        0                 || 1
    }

    def 'should consume DestinationRequest and add destination to specified elevator'() {
        given:
        initSystem()

        when:
        service.destination(getDestinationRequest())

        then:
        Queue<Integer> result = service.getElevators().get(0).getDestinationFloors()
        verifyAll {
            result.size() == 1
            result.peek() == 5
        }
    }

    def 'should move specific elevator one step forward'() {
        given:
        getElevatorsWithOneDestination()

        when:
        service.step()

        then:
        Elevator result = service.elevators.get(0)
        verifyAll {
            result.getCurrentFloor() == 1
            result.status() == ElevatorStatus.OCCUPIED
            result.direction() == ElevatorDirection.UP
        }
    }

    def 'should update destinationFloor with UpdateRequest'() {
        given:
        getElevatorsWithOneDestination()

        when:
        service.update(getUpdateRequest())

        then:
        Elevator result = service.elevators.get(0)
        result.getDestinationFloors().peek().intValue() == 4
    }

    def 'should throw WrongUpdateRequestException when wrong oldDestinationFloor appear'() {
        given:
        getElevatorsWithOneDestination()

        when:
        service.update(getWrongUpdateRequest())

        then:
        thrown(WrongUpdateRequestException)
    }

    def 'should update destinationFloor when got other destinations in queue'() {
        given:
        getElevatorWithTwoDestinations()

        when:
        service.update(new UpdateRequest(0, 5, 10))

        then:
        Elevator result = service.elevators.get(0)
        result.getDestinationFloors() == [10, 8]
    }

    def 'should return elevators system status'() {
        given:
        getElevatorsWithOneDestination()

        when:
        String result = service.elevatorsStatus()

        then:
        result == '[Elevator(id=0, currentFloor=0, destinationFloors=[5]), ' +
                'Elevator(id=1, currentFloor=0, destinationFloors=[]), ' +
                'Elevator(id=2, currentFloor=0, destinationFloors=[])]'

    }

    private void initSystem() {
        service.initializeElevators(getInitializeRequest())
    }

    private void getElevatorsWithOneDestination() {
        initSystem()
        service.destination(getDestinationRequest())
    }

    private void getElevatorWithTwoDestinations() {
        getElevatorsWithOneDestination()
        service.destination(getSecondDestinationRequest())
    }
}
