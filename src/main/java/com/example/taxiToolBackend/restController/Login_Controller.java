package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.Customer_Log;
import com.example.taxiToolBackend.data.Employee_Log;
import com.example.taxiToolBackend.repository.CustomerLog_Repository;
import com.example.taxiToolBackend.repository.EmployeeLog_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class, it is responsible for processing login requests.
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-01-23
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class Login_Controller {
    private final EmployeeLog_Repository userLogRepository;
    private final CustomerLog_Repository customerLogRepository;

    @Autowired
    public Login_Controller(EmployeeLog_Repository userLogRepository, CustomerLog_Repository customerLogRepository) {

        this.userLogRepository = userLogRepository;
        this.customerLogRepository = customerLogRepository;
    }

    /**
     * Handles the "/getLogin" endpoint to retrieve login information based on user roles.
     *
     * @param userDetails Authentication details of the logged-in user.
     * @return A JSON string containing user role and related information.
     */
    @GetMapping("/getLogin")
    public String getLogin(@AuthenticationPrincipal UserDetails userDetails) {

        String tempUser = "";
        String username = userDetails.getUsername();
        Employee_Log employeeLog;
        Customer_Log customerLog;

        // Check if user details are not null
        if (userDetails != null) {
            String role = userDetails.getAuthorities().iterator().next().getAuthority();

            // Retrieve user-specific information based on the role
            if(role.equals("ROLE_ADMIN")|| role.equals("ROLE_DRIVER")) {
                employeeLog = userLogRepository.findByUsername(username);
                tempUser = employeeLog.getEmployees().getPersonalID();

            } else if (role.equals("ROLE_CUSTOMER")) {
                customerLog = customerLogRepository.findByUsername(username);
                tempUser = customerLog.getCustomers().getFahrgast();
            }

            // Build and return a JSON result
            String jsonResult = "{\"role\": \"" + role + "\", \"name\": \"" + tempUser + "\"}";
            return jsonResult;
        } else {
            // Return a message if user details are null
            return "Benutzerdetails sind null";
        }
    }
}
