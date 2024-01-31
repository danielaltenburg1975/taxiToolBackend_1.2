package com.example.taxiToolBackend.repository;



import com.example.taxiToolBackend.data.Employee_Log;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EmployeeLog_Repository extends CrudRepository<Employee_Log, Long> {
    Employee_Log findByUsername(String username);
    @Query("SELECT cl FROM Employee_Log cl WHERE cl.employees.personalID = :personalID")
    Optional<Employee_Log> findByEmployeeName(@Param("personalID") String personalID);
}
