package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.dataRequest.RouteRequest;
import com.example.taxiToolBackend.dataRequest.TimeRequest;
import com.example.taxiToolBackend.tripServices.GraphHopperService;
import com.graphhopper.util.PointList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



/**
 * The TripService_Controller class provides RESTful endpoints for calculating trip details.
 * The methods receive the start and end coordinates for the calculation.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 19.01.2023
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TripService_Controller {
    private final GraphHopperService graphHopperService;

    @Autowired
    public TripService_Controller(GraphHopperService graphHopperService){
        this.graphHopperService = graphHopperService;
    }

    //The method accepts the data for calculating the
    //travel costs and returns the price and kilometers.
    @PostMapping("/calculateTrip")
    public ResponseEntity<String> calculateTrip(@RequestBody RouteRequest routeRequest) {

        try {
            //check if the input datta is valid
            if (isValid(routeRequest)) {
                // Extracting values from the RouteRequest
                double startLat = routeRequest.getStartLat();
                double startLon = routeRequest.getStartLon();
                double endLat = routeRequest.getEndLat();
                double endLon = routeRequest.getEndLon();

                String tempGebiet = routeRequest.getRequestGebiet();
                String tempAnfahrt = routeRequest.getRequestAnfahrt();
                String tempGrossraum = routeRequest.getRequestGrossraum();

                PointList routePoints = graphHopperService.getRoutePoints(startLat, startLon, endLat, endLon);

                // Check if routePoints are not null
                if (routePoints != null) {
                    // Build a JSON response with calculated values
                    return ResponseEntity.ok("{\"Preis\": \"" + graphHopperService.calculateTrip(tempGebiet, tempAnfahrt, tempGrossraum)
                            + "\", \"Entfernung\": \"" + graphHopperService.getTripDistance() + "\"}");
                } else {
                    // Return an error response if routePoints are null
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting route points");
                }
            } else {
                // Return a bad request response if input data is invalid
                return ResponseEntity.badRequest().body("Invalid input data");
            }
        } catch (Exception e) {
            // Return an internal server error response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    //The method receives the data for determining
    // the departure time and returns it.
    @PostMapping("/calculateDepartureTime")
    public ResponseEntity<String> calculateDepartureTime(@RequestBody TimeRequest timeRequest) {

        try {
            //check if the input datta is valid
            if (isValid(timeRequest)) {
                // Extracting values from the TimeRequest
                double startLat = timeRequest.getStartLat();
                double startLon = timeRequest.getStartLon();
                double endLat = timeRequest.getEndLat();
                double endLon = timeRequest.getEndLon();

                String tempTermin = timeRequest.getRequestTermin();
                String tempLaufweg = timeRequest.getRequestLaufweg();
                String tempPuffer = timeRequest.getRequestPuffer();

                PointList routePoints = graphHopperService.getRoutePoints(startLat, startLon, endLat, endLon);

                // Check if routePoints are not null
                if (routePoints != null) {
                    // Build a JSON response with calculated values
                    return ResponseEntity.ok("{\"Abfahrt\": \"" + graphHopperService.calulateTime(tempTermin, tempLaufweg, tempPuffer)+ "\"}");
                } else {
                    // Return an error response if routePoints are null
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting route points");
                }
            } else {
                // Return a bad request response if input data is invalid
                return ResponseEntity.badRequest().body("Invalid input data");
            }
        } catch (Exception e) {
            // Return an internal server error response if an exception occurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Validation methods to check if the input data is valid
    private boolean isValid(RouteRequest routeRequest) {

        return routeRequest != null
                && routeRequest.getStartLat() != 0
                && routeRequest.getStartLon() != 0
                && routeRequest.getEndLat() != 0
                && routeRequest.getEndLon() != 0;
    }

    private boolean isValid(TimeRequest timeRequest) {

        return timeRequest != null
                && timeRequest.getStartLat() != 0
                && timeRequest.getStartLon() != 0
                && timeRequest.getEndLat() != 0
                && timeRequest.getEndLon() != 0;
    }


}
