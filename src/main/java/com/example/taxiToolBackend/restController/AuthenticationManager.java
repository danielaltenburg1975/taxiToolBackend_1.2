package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.Customer_Log;
import com.example.taxiToolBackend.data.Customers;
import com.example.taxiToolBackend.data.Employee_Log;
import com.example.taxiToolBackend.data.Employees;
import com.example.taxiToolBackend.dataRequest.Customer_LogRequest;
import com.example.taxiToolBackend.dataRequest.Employee_LogRequest;
import com.example.taxiToolBackend.repository.CustomerLog_Repository;
import com.example.taxiToolBackend.repository.Customer_Repository;
import com.example.taxiToolBackend.repository.EmployeeLog_Repository;
import com.example.taxiToolBackend.repository.Employees_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class AuthenticationManager {

    private final CustomerLog_Repository customerLogRepository;
    private final Customer_Repository customerRepository;
    private final EmployeeLog_Repository employeeLogRepository;
    private final Employees_Repository employeesRepository;

    @Autowired
    public AuthenticationManager(CustomerLog_Repository customerLogRepository, EmployeeLog_Repository employeeLogRepository,
                                 Customer_Repository customerRepository, Employees_Repository employeesRepository){
        this.customerLogRepository = customerLogRepository;
        this.customerRepository = customerRepository;
        this.employeeLogRepository = employeeLogRepository;
        this.employeesRepository = employeesRepository;
    }
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/getAllCustomerLogs")
    public  List<Customer_LogRequest> getAllCustomerLogs() {
        // Invoke the CustomerLog_Repository to retrieve all customer logs
        Iterable<Customer_Log> allLogs = customerLogRepository.findAll();

        // Initialize a list to store the converted Customer_LogRequest objects
        List<Customer_LogRequest> responseList = new ArrayList<>();

        // Iterate through all logs and convert them into Customer_LogRequest objects
        for (Customer_Log log : allLogs) {
            // Create a Customer_LogRequest object and set the attributes accordingly
            Customer_LogRequest response = new Customer_LogRequest(
                    log.getCustomers().getFahrgast(),
                    log.getUsername(),
                    "vertraulich",
                    log.getRoles()
            );
            responseList.add(response);
        }

        // Return the list of converted objects
        return responseList;
    }

    @GetMapping("/getAllEmployeeLogs")
    public  List<Employee_LogRequest> getAllEmployeeLogs() {
        // Invoke the CustomerLog_Repository to retrieve all customer logs
        Iterable<Employee_Log> allLogs = employeeLogRepository.findAll();

        // Initialize a list to store the converted Customer_LogRequest objects
        List<Employee_LogRequest> responseList = new ArrayList<>();

        // Iterate through all logs and convert them into Customer_LogRequest objects
        for (Employee_Log log : allLogs) {
            // Create a Customer_LogRequest object and set the attributes accordingly
            Employee_LogRequest response = new Employee_LogRequest(
                    log.getEmployees().getPersonalID(),
                    log.getUsername(),
                    "vertraulich",
                    log.getRoles()
            );
            responseList.add(response);
        }

        // Return the list of converted objects
        return responseList;
    }

    @PutMapping("/updateCustomerLogs")
    public ResponseEntity<Customer_LogRequest> updateCustomerLogs(@RequestBody Map<String, Object> requestData) {
        // Extract values from the JSON request body
        String name = (String) requestData.get("name");
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String roles = (String) requestData.get("roles");

        // Invoke the CustomerLog_Repository to search for a customer log with the specified name
        Optional<Customer_Log> foundLog = customerLogRepository.findByCustomerName(name);

        // Check if a log with the specified name was found
        if (foundLog.isPresent()) {
            // Update the found log with the new values
            Customer_Log log = foundLog.get();
            log.setUsername(username);

            // Encrypt the new password before setting it
            String encryptedPassword = passwordEncoder.encode(password);
            log.setPassword(encryptedPassword);

            log.setRoles(roles);

            // Save the updated log
            customerLogRepository.save(log);

            // Convert the updated log into a Customer_LogRequest object
            Customer_LogRequest response = new Customer_LogRequest(
                    name,
                    log.getUsername(),
                    "vertraulich",
                    log.getRoles()
                    // Set other attributes as needed
            );

            // Return the converted object with a 200 OK response
            return ResponseEntity.ok(response);
        } else {
            // Return a 404 Not Found response if no log with the specified name was found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/updateEmployeeLogs")
    public ResponseEntity<Employee_LogRequest> updateEmployeeLogs(@RequestBody Map<String, Object> requestData) {
        // Extract values from the JSON request body
        String personalID = (String) requestData.get("personalID");
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String roles = (String) requestData.get("roles");

        // Invoke the CustomerLog_Repository to search for a customer log with the specified name
        Optional<Employee_Log> foundLog = employeeLogRepository.findByEmployeeName(personalID);

        // Check if a log with the specified name was found
        if (foundLog.isPresent()) {
            // Update the found log with the new values
            Employee_Log log = foundLog.get();
            log.setUsername(username);

            // Encrypt the new password before setting it
            String encryptedPassword = passwordEncoder.encode(password);
            log.setPassword(encryptedPassword);

            log.setRoles(roles);

            // Save the updated log
            employeeLogRepository.save(log);

            // Convert the updated log into a Customer_LogRequest object
            Employee_LogRequest response = new Employee_LogRequest(
                    personalID,
                    log.getUsername(),
                    "vertraulich",
                    log.getRoles()
                    // Set other attributes as needed
            );

            // Return the converted object with a 200 OK response
            return ResponseEntity.ok(response);
        } else {
            // Return a 404 Not Found response if no log with the specified name was found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createEmployeeLog")
    public ResponseEntity<Employee_LogRequest> createEmployeeLog(@RequestBody Map<String, Object> requestData) {
        // Extract values from the JSON request body
        String personalID = (String) requestData.get("personalID");
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String roles = (String) requestData.get("roles");

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(password);

        // Find the Customer by name
        Employees employees = employeesRepository.findByPersonalID(personalID);

        if (employees != null) {
            // Create a new Customer_Log object
            Employee_Log newLog = new Employee_Log();
            newLog.setEmployees(employees);
            newLog.setUsername(username);
            newLog.setPassword(encryptedPassword);
            newLog.setRoles(roles);

            // Save the new log
            employeeLogRepository.save(newLog);

            // Convert the new log into a Customer_LogRequest object
            Employee_LogRequest response = new Employee_LogRequest(
                    personalID,
                    username,
                    "vertraulich",
                    roles
                    // Set other attributes as needed
            );

            // Return the converted object with a 201 Created response
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // Handle the case where no customer with the given name was found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/createCustomerLog")
    public ResponseEntity<Customer_LogRequest> createCustomerLog(@RequestBody Map<String, Object> requestData) {
        // Extract values from the JSON request body
        String name = (String) requestData.get("name");
        String username = (String) requestData.get("username");
        String password = (String) requestData.get("password");
        String roles = (String) requestData.get("roles");

        // Encrypt the password before saving
        String encryptedPassword = passwordEncoder.encode(password);

        // Find the Customer by name
        Customers customer = customerRepository.findByFahrgast(name);

        if (customer != null) {
            // Create a new Customer_Log object
            Customer_Log newLog = new Customer_Log();
            newLog.setCustomers(customer);
            newLog.setUsername(username);
            newLog.setPassword(encryptedPassword);
            newLog.setRoles(roles);

            // Save the new log
            customerLogRepository.save(newLog);

            // Convert the new log into a Customer_LogRequest object
            Customer_LogRequest response = new Customer_LogRequest(
                    name,
                    username,
                    "vertraulich",
                    roles
                    // Set other attributes as needed
            );

            // Return the converted object with a 201 Created response
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            // Handle the case where no customer with the given name was found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
