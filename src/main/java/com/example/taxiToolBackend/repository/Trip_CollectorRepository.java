package com.example.taxiToolBackend.repository;



import com.example.taxiToolBackend.data.Trip_Collector;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Trip_CollectorRepository extends CrudRepository<Trip_Collector,Integer > {

    @Query("SELECT tc FROM tripcollector tc WHERE tc.customers.fahrgast = :fahrgast")
    Iterable<Trip_Collector> findByCustomerName(@Param("fahrgast") String fahrgast);


}
