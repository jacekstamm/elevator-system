package com.system.elevator.utils

import com.system.elevator.request.DestinationRequest
import com.system.elevator.request.InitializeRequest
import com.system.elevator.request.UpdateRequest

class RequestConstants {

    static InitializeRequest getInitializeRequest() {
        return new InitializeRequest(3,10)
    }

    static DestinationRequest getDestinationRequest() {
        return new DestinationRequest(0, 5)
    }

    static DestinationRequest getSecondDestinationRequest() {
        return new DestinationRequest(0, 8)
    }
    static UpdateRequest getUpdateRequest() {
        return new UpdateRequest(0, 5, 4)
    }

    static UpdateRequest getWrongUpdateRequest() {
        return new UpdateRequest(0, 4, 10)
    }
}
