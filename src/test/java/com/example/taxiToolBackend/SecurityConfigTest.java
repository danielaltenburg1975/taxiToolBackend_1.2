package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.Customer_Log;
import com.example.taxiToolBackend.data.Employee_Log;
import com.example.taxiToolBackend.repository.CustomerLog_Repository;
import com.example.taxiToolBackend.repository.EmployeeLog_Repository;
import com.example.taxiToolBackend.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @Mock
    private EmployeeLog_Repository userRepository;

    @Mock
    private CustomerLog_Repository customerRepository;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void testUserDetailsServiceForEmployee() {
        Employee_Log employeeLog = new Employee_Log();
        employeeLog.setUsername("admin");
        employeeLog.setPassword(new BCryptPasswordEncoder().encode("password"));
        employeeLog.setRoles("ADMIN");
        when(userRepository.findByUsername(any())).thenReturn(employeeLog);

        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        assertEquals("admin", userDetails.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("password", userDetails.getPassword()));
        assertEquals("ROLE_ADMIN", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testUserDetailsServiceForCustomer() {
        Customer_Log customerLog = new Customer_Log();
        customerLog.setUsername("customer");
        customerLog.setPassword(new BCryptPasswordEncoder().encode("password"));
        customerLog.setRoles("CUSTOMER");
        when(customerRepository.findByUsername(any())).thenReturn(customerLog);

        UserDetailsService userDetailsService = securityConfig.userDetailsService();
        UserDetails userDetails = userDetailsService.loadUserByUsername("customer");

        assertEquals("customer", userDetails.getUsername());
        assertTrue(new BCryptPasswordEncoder().matches("password", userDetails.getPassword()));
        assertEquals("ROLE_CUSTOMER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    void testUserDetailsServiceUserNotFound() {
        when(userRepository.findByUsername(any())).thenReturn(null);
        when(customerRepository.findByUsername(any())).thenReturn(null);

        UserDetailsService userDetailsService = securityConfig.userDetailsService();

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistent"));
    }


}
