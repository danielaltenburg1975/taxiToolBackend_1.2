package com.example.taxiToolBackend.security;



import com.example.taxiToolBackend.data.Customer_Log;
import com.example.taxiToolBackend.data.Employee_Log;
import com.example.taxiToolBackend.repository.CustomerLog_Repository;
import com.example.taxiToolBackend.repository.EmployeeLog_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Configuration class for Spring Security.
 * <p>
 * This class defines security configurations, including authentication,
 * authorization rules, and password encoding. It uses a custom
 * UserDetailsService to load user details based on the provided username.
 * <p>
 * The class also configures security filters and rules using HttpSecurity.
 * Security rules specify which roles are required to access certain endpoints.
 * Additionally, a PasswordEncoder bean is configured for encrypting passwords.
 * <p>
 * Important! This is a demo version. The security configuration is limited to
 * a minimum to keep the connection to the frontend simple. In production,
 * further elements must be implemented such as a token generator and https.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2023-12-21
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;
    private final EmployeeLog_Repository userRepository;
    private final CustomerLog_Repository customerRepository;

    /**
     * Constructor for SecurityConfig.
     *
     * @param dataSource          The data source for authentication.
     * @param userRepository      Repository for accessing employee data.
     * @param customerRepository  Repository for accessing customer data.
     */
    @Autowired
    public SecurityConfig(DataSource dataSource, EmployeeLog_Repository userRepository,
                          CustomerLog_Repository customerRepository) {
        this.dataSource = dataSource;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
    }


    /**
     * Configures a custom UserDetailsService to load user details based on the provided username.
     *
     * @return An implementation of the UserDetailsService interface.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Employee_Log employeeLog = userRepository.findByUsername(username);
                Customer_Log customerLog = customerRepository.findByUsername(username);

                if (employeeLog != null) {
                    return org.springframework.security.core.userdetails.User.builder()
                            .username(employeeLog.getUsername())
                            .password(employeeLog.getPassword())
                            .roles(employeeLog.getRoles())
                            .build();

                } else if (customerLog != null) {
                    return org.springframework.security.core.userdetails.User.builder()
                            .username(customerLog.getUsername())
                            .password(customerLog.getPassword())
                            .roles(customerLog.getRoles())
                            .build();

                } else {
                    throw new UsernameNotFoundException("User not found: " + username);
                }
            }
        };
    }

    /**
     * Configures security filters and rules using HttpSecurity.
     *
     * @param http HttpSecurity instance to configure security settings.
     * @return SecurityFilterChain configured with specified rules.
     * @throws Exception If configuration encounters an exception.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // !! I do not use tokens in the demo version
                .authorizeRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET, "/getLogin/**").hasAnyRole("ADMIN", "DRIVER", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/searchCustomerData/**").hasAnyRole("ADMIN", "DRIVER")
                        .requestMatchers(HttpMethod.GET, "/searchCustomerTrips/**").hasAnyRole("ADMIN", "CUSTOMER")
                        .requestMatchers(HttpMethod.GET,"/getAllTripInfo/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/getTripInfo/{gebiet}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/searchTaxiStamp/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/getAllCustomerLogs/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/getAllEmployeeLogs/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/createCustomerLog/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/createEmployeeLog/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/createTripInfo/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/setTaxiStamp/**").hasAnyRole("ADMIN", "Driver")
                        .requestMatchers(HttpMethod.POST,"/calculateTrip/**").hasAnyRole("ADMIN","DRIVER","CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/calculateDepartureTime/**").hasAnyRole("ADMIN","DRIVER","CUSTOMER")
                        .requestMatchers(HttpMethod.POST,"/setNewTrip/**").hasAnyRole("ADMIN","DRIVER","CUSTOMER")

                        .requestMatchers(HttpMethod.PUT, "/getTripInfo/{gebiet}/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/updateCustomerLogs/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/updateEmployeeLogs/**").hasAnyRole("ADMIN")

                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Configures a PasswordEncoder bean for encrypting passwords.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
