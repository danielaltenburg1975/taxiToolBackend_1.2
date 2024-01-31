package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.New_Trip;
import com.example.taxiToolBackend.dataRequest.New_TripRequest;
import com.example.taxiToolBackend.repository.Customer_Repository;
import com.example.taxiToolBackend.repository.Employees_Repository;
import com.example.taxiToolBackend.repository.New_TripRepository;
import com.example.taxiToolBackend.restController.New_TripController;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class New_TripControllerTest {

    @Test
    void getNewTripReturnsList() {
        // Arrange
        New_TripRepository newTripRepository = mock(New_TripRepository.class);
        Customer_Repository customerRepository = mock(Customer_Repository.class);
        Employees_Repository employeesRepository = mock(Employees_Repository.class);
        New_TripController controller = new New_TripController(newTripRepository, customerRepository, employeesRepository);

        List<New_Trip> trips = new ArrayList<>();
        trips.add(new New_Trip());

        when(newTripRepository.findAll()).thenReturn(trips);

        // Act
        ResponseEntity<List<New_TripRequest>> response = controller.getNewTrip();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void postNewTripReturnsBadRequestWhenCustomerNotFound() {
        // Arrange
        New_TripRepository newTripRepository = mock(New_TripRepository.class);
        Customer_Repository customerRepository = mock(Customer_Repository.class);
        Employees_Repository employeesRepository = mock(Employees_Repository.class);
        New_TripController controller = new New_TripController(newTripRepository, customerRepository, employeesRepository);

        New_TripRequest newTripRequest = new New_TripRequest();
        newTripRequest.setFahrgast("NonExistentCustomer");

        when(customerRepository.findByFahrgast("NonExistentCustomer")).thenReturn(null);

        // Act
        ResponseEntity<New_TripRequest> response = controller.postNewTrip(newTripRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(null, response.getBody());
    }


}

