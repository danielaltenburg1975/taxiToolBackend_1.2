package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.Trip_Collector;
import com.example.taxiToolBackend.dataRequest.Search_TripRequest;
import com.example.taxiToolBackend.repository.Trip_CollectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * The customer area has endpoints that only affect the customer.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-01-24
 */

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class CustomerArea_Controller {
    private final Trip_CollectorRepository tripCollectorRepository;

    @Autowired
    public CustomerArea_Controller(Trip_CollectorRepository tripCollectorRepository) {
        this.tripCollectorRepository = tripCollectorRepository;
    }

    /**
     * Searches for customer trips based on the customer name parameter and returns the results.
     *
     * @param name The name of the customer for whom trips are to be searched.
     * @return An Iterable of Search_TripRequest objects representing the found trips.
     */

    @GetMapping("/searchCustomerTrips")
    public Iterable<Search_TripRequest> searchCustomerTrips(@RequestParam String name) {
        // Invoke the Trip_CollectorRepository to search for trips with the specified customer name
        Iterable<Trip_Collector> foundTrips = tripCollectorRepository.findByCustomerName(name);

        // Initialize a list to store the converted Search_TripRequest objects
        List<Search_TripRequest> responseList = new ArrayList<>();

        // Iterate through the found trips and convert them into Search_TripRequest objects
        for (Trip_Collector trip : foundTrips) {
            // Create a Search_TripRequest object and set the attributes accordingly
            Search_TripRequest response = new Search_TripRequest(
                    name,
                    trip.getAbholort(),
                    trip.getZielort(),
                    trip.getZeit(),
                    trip.getAnmerkung()
            );
            responseList.add(response);
        }

        return responseList;
    }

}
