# Elevator Control System

## Requirements:
1) Java 11

## API Endpoint Description:

1) POST -> `elevator/initializeSystem` - Initialize System with InitializeRequest

Example of InitializeRequest:

{
    "numberOfElevators" : 4, (max 16)
    "numberOfFloors": 10 (max 30)
}

2) POST -> `elevator/pickUp` - Picking Up Elevator from specific floor

Example of PickUpRequest:

{
    "elevatorId" : 0,
    "pickUpFloor" : 2
}

3) POST -> `elevator/destination` - Adding new destination to queue

Example of DestinationRequest:

{
    "elevatorId" : 0,
    "destinationFloor" : 7
}

4) PUT -> `elevator/update` - Update first destination in Elevator queue

Example of UpdateRequest:

{
    "elevatorId" : 0,
    "oldDestination" : 10,
    "newDestination" : 9
}

5) POST -> `elevator/step` - Make one step in elevator system
6) GET -> `elevator/status` - Check System Status

Command Line run:
1) Application `gradle bootRun`
2) All Tests `gradle test`