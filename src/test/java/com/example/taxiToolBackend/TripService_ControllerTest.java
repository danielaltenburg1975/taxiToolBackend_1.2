package com.example.taxiToolBackend;


import com.example.taxiToolBackend.dataRequest.RouteRequest;
import com.example.taxiToolBackend.dataRequest.TimeRequest;
import com.example.taxiToolBackend.restController.TripService_Controller;
import com.example.taxiToolBackend.tripServices.GraphHopperService;
import com.graphhopper.util.PointList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TripService_ControllerTest {

    @Mock
    private GraphHopperService graphHopperService;

    @InjectMocks
    private TripService_Controller tripServiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCalculateTripWithValidRoute() {
        // Mocking the behavior of graphHopperService
        when(graphHopperService.getRoutePoints(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(mock(PointList.class));
        when(graphHopperService.calculateTrip(anyString(), anyString(), anyString())).thenReturn("10");
        when(graphHopperService.getTripDistance()).thenReturn(5);

        // Creating a sample RouteRequest
        RouteRequest routeRequest = new RouteRequest();
        routeRequest.setStartLat(1.0);
        routeRequest.setStartLon(2.0);
        routeRequest.setEndLat(3.0);
        routeRequest.setEndLon(4.0);
        routeRequest.setRequestGebiet("exampleGebiet");
        routeRequest.setRequestAnfahrt("exampleAnfahrt");
        routeRequest.setRequestGrossraum("exampleGrossraum");

        // Calling the method to be tested
        ResponseEntity<String> result = tripServiceController.calculateTrip(routeRequest);

        // Assertions
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("{\"Preis\": \"10\", \"Entfernung\": \"5\"}", result.getBody());

        // Verify that the graphHopperService methods were called with the expected arguments
        verify(graphHopperService, times(1))
                .getRoutePoints(1.0, 2.0, 3.0, 4.0);
        verify(graphHopperService, times(1))
                .calculateTrip("exampleGebiet", "exampleAnfahrt", "exampleGrossraum");
        verify(graphHopperService, times(1)).getTripDistance();
    }
    @Test
    void testCalculateDepartureTimeWithInvalidTimeRequest() {
        // Mocking the behavior of graphHopperService
        when(graphHopperService.getRoutePoints(anyDouble(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(mock(PointList.class));
        when(graphHopperService.calulateTime(anyString(), anyString(), anyString())).thenReturn("12:30");

        // Creating a sample invalid TimeRequest
        TimeRequest timeRequest = new TimeRequest();
        timeRequest.setStartLat(1.0);
        timeRequest.setStartLon(2.0);

        // Calling the method to be tested
        ResponseEntity<String> result = tripServiceController.calculateDepartureTime(timeRequest);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Invalid input data", result.getBody());

        // Verify that the graphHopperService methods were not called
        verify(graphHopperService, never()).calulateTime(anyString(), anyString(), anyString());
    }

}
