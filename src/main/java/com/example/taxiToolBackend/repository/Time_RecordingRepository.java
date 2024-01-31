package com.example.taxiToolBackend.repository;



import com.example.taxiToolBackend.data.Time_Recording;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface Time_RecordingRepository extends CrudRepository<Time_Recording,Integer > {

    // Zähle die Anzahl der Einträge für einen bestimmten Benutzer am gleichen Tag
    @Query("SELECT COUNT(tr) FROM Time_Booking tr WHERE tr.personalnummer = :personalnummer " +
            "AND tr.zeitbuchung BETWEEN :startOfDay AND :endOfDay")
    long countEntries(@Param("personalnummer") String personalnummer,
                      @Param("startOfDay") String startOfDay,
                      @Param("endOfDay") String endOfDay);

}
