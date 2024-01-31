package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.Customers;
import com.example.taxiToolBackend.data.Employees;
import com.example.taxiToolBackend.data.New_Trip;
import com.example.taxiToolBackend.dataRequest.New_TripRequest;
import com.example.taxiToolBackend.repository.Customer_Repository;
import com.example.taxiToolBackend.repository.Employees_Repository;
import com.example.taxiToolBackend.repository.New_TripRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Controller class for receiving and listing new trips
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-01-23
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class New_TripController {

    public final New_TripRepository newTripRepository;
    private final Customer_Repository customersRepository;
    private final Employees_Repository employeesRepository;

    public New_TripController(New_TripRepository newTripRepository, Customer_Repository customerRepository,
                              Employees_Repository employeesRepository) {

        this.newTripRepository = newTripRepository;
        this.customersRepository = customerRepository;
        this.employeesRepository = employeesRepository;
    }


    /**
     * Handles HTTP GET request to retrieve a list of New_TripRequest objects.
     * @return ResponseEntity containing a list of New_TripRequest objects.
     */
    @GetMapping("/getNewTrip")
    public ResponseEntity<List<New_TripRequest>> getNewTrip() {
        if (newTripRepository != null) {
            Iterable<New_Trip> newTripsIterable = newTripRepository.findAll();
            List<New_Trip> newTrips = StreamSupport.stream(newTripsIterable.spliterator(), false)
                    .collect(Collectors.toList());

            // Convert New_Trip objects to New_TripRequest objects
            List<New_TripRequest> newTripRequests = newTrips.stream()
                    .map(this::convertNewTrip)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(newTripRequests);
        } else {

            // Return HTTP status 500 (Internal Server Error) if repository is null
            return ResponseEntity.status(500).body(new ArrayList<>());
        }
    }

    /**
     * Handles HTTP POST request to create a new New_Trip and returns the associated New_TripRequest.
     *
     * @param newTripRequest New_TripRequest object containing trip details.
     * @return ResponseEntity containing the created New_TripRequest.
     */
    @PostMapping("/setNewTrip")
    public ResponseEntity<New_TripRequest> postNewTrip(@RequestBody New_TripRequest newTripRequest) {
        String personalID = newTripRequest.getFahrer();

        // Find the customer based on the passenger name
        Customers customer = customersRepository.findByFahrgast(newTripRequest.getFahrgast());

        if (customer == null) {
            // Return HTTP status 400 (Bad Request) if customer is not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Find the driver based on the employee ID
        Employees employees = employeesRepository.findByPersonalID(newTripRequest.getFahrer());
        if (employees == null) {
            // Return HTTP status 400 (Bad Request) if driver is not found
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Create a new New_Trip object and set the values
        New_Trip newTrip = new New_Trip();
        newTrip.setAbholort(newTripRequest.getAbholort());
        newTrip.setAnmerkung(newTripRequest.getAnmerkung());
        newTrip.setGebucht(newTripRequest.getGebucht());
        newTrip.setZeit(newTripRequest.getZeit());
        newTrip.setZielort(newTripRequest.getZielort());
        newTrip.setCustomers(customer);
        newTrip.setEmployees(employees);

        /// Save the New_Trip object to the database
        New_Trip savedTrip = newTripRepository.save(newTrip);

        // Convert the saved New_Trip object to a New_TripRequest object with limited information
        New_TripRequest savedTripRequest = convertNewTrip(savedTrip);

        // Return HTTP status 201 (Created) with the created
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTripRequest);
    }


    /**
     * I don't need all the database entries to return the Post and Get methods.
     * The method conventes a new object for the corresponding outputs.
     *
     * @param newTrip The New_Trip object to convert.
     * @return A New_TripRequest object with converted information.
     */
    private New_TripRequest convertNewTrip(New_Trip newTrip) {
        New_TripRequest newTripRequest = new New_TripRequest();

        // Retrieve and set the driver information
        Employees employees = newTrip.getEmployees();
        if (employees != null) {
            newTripRequest.setFahrer(employees.getPersonalID());
        } else {
            // Handle the case when the employee is null
            newTripRequest.setFahrer("UnknownDriver");
        }

        // Retrieve and set the passenger information
        Customers customer = newTrip.getCustomers();
        if (customer != null) {
            newTripRequest.setFahrgast(customer.getFahrgast());
        } else {
            // Handle the case when the customer is null
            newTripRequest.setFahrgast("UnknownCustomer");
        }
        // Set other trip details
        newTripRequest.setAbholort(newTrip.getAbholort());
        newTripRequest.setZielort(newTrip.getZielort());
        newTripRequest.setZeit(newTrip.getZeit());
        newTripRequest.setGebucht(newTrip.getGebucht());
        newTripRequest.setAnmerkung(newTrip.getAnmerkung());

        return newTripRequest;
    }


}
