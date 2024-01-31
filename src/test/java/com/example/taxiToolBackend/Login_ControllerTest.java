package com.example.taxiToolBackend;



import com.example.taxiToolBackend.data.Customer_Log;
import com.example.taxiToolBackend.data.Customers;
import com.example.taxiToolBackend.data.Employee_Log;
import com.example.taxiToolBackend.data.Employees;
import com.example.taxiToolBackend.repository.CustomerLog_Repository;
import com.example.taxiToolBackend.repository.EmployeeLog_Repository;
import com.example.taxiToolBackend.restController.Login_Controller;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Login_ControllerTest {

    @Mock
    private EmployeeLog_Repository employeeLogRepository;

    @Mock
    private CustomerLog_Repository customerLogRepository;

    @InjectMocks
    private Login_Controller loginController;

    @Test
    void testGetLoginForAdminRole() {
        Employee_Log employeeLog = new Employee_Log();
        // Stellen Sie sicher, dass Employees-Objekt initialisiert wird, bevor Sie darauf zugreifen.
        employeeLog.setEmployees(new Employees());
        employeeLog.getEmployees().setPersonalID("AdminUser");
        when(employeeLogRepository.findByUsername(any())).thenReturn(employeeLog);

        UserDetails userDetails = new User("admin", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        String result = loginController.getLogin(userDetails);

        assertEquals("{\"role\": \"ROLE_ADMIN\", \"name\": \"AdminUser\"}", result);
    }

    @Test
    void testGetLoginForCustomerRole() {
        Customer_Log customerLog = new Customer_Log();
        // Stellen Sie sicher, dass Customers-Objekt initialisiert wird, bevor Sie darauf zugreifen.
        customerLog.setCustomers(new Customers());
        customerLog.getCustomers().setFahrgast("CustomerUser");
        when(customerLogRepository.findByUsername(any())).thenReturn(customerLog);

        UserDetails userDetails = new User("customer", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_CUSTOMER")));

        String result = loginController.getLogin(userDetails);

        assertEquals("{\"role\": \"ROLE_CUSTOMER\", \"name\": \"CustomerUser\"}", result);
    }



}
