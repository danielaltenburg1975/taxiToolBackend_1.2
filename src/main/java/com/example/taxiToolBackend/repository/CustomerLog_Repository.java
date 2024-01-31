package com.example.taxiToolBackend.repository;




import com.example.taxiToolBackend.data.Customer_Log;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CustomerLog_Repository extends CrudRepository<Customer_Log, Long> {
    Customer_Log findByUsername(String username);

    @Query("SELECT cl FROM Customer_Log cl WHERE cl.customers.fahrgast = :fahrgast")
    Optional<Customer_Log> findByCustomerName(@Param("fahrgast") String fahrgast);
}
