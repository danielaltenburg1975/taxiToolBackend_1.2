package com.example.taxiToolBackend.repository;


import com.example.taxiToolBackend.data.Customers;
import org.springframework.data.repository.CrudRepository;

public interface Customer_Repository extends CrudRepository<Customers,Integer > {
    Customers findByFahrgast(String fahrgast);
}
