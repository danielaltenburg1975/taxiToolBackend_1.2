package com.example.taxiToolBackend.repository;


import com.example.taxiToolBackend.data.Employees;
import org.springframework.data.repository.CrudRepository;

public interface Employees_Repository extends CrudRepository<Employees,Integer > {
    Employees findByPersonalID(String personalnummer);
}
