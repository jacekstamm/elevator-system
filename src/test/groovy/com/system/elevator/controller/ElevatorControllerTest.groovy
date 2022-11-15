package com.system.elevator.controller

import com.google.gson.Gson
import com.system.elevator.request.InitializeRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static com.system.elevator.utils.RequestConstants.getInitializeRequest

@SpringBootTest
@AutoConfigureMockMvc
class ElevatorControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    private Gson gson = new Gson()


    def 'should initialize system with InitializeRequest'() {
        when:
        def result = mockMvc.perform(post('/elevator/initializeSystem')
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(getInitializeRequest())))
                .andExpect(status().isOk())
                .andReturn().response

        then:
        result.getContentAsString() == 'System was initialized with 3 elevators operating over 5 floors.'
    }

    @Unroll
    def 'should throw BAD REQUEST Status <400> when invalid InitializeRequest appear'() {
        given:
        InitializeRequest wrongRequest = new InitializeRequest(numberOfFloors, numberOfElevators)

        when:
        def result =mockMvc.perform(post('/elevator/initializeSystem')
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(wrongRequest)))
                .andExpect(status().isBadRequest())
                .andReturn().response

        then:
        result.getContentAsString() == 'Elevator or number of floors must be greater that 0!'

        where:
        numberOfElevators || numberOfFloors
        1                 || 0
        0                 || 1
    }
}
