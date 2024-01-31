package com.example.taxiToolBackend.repository;


import com.example.taxiToolBackend.data.AdminSettings;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AdminSettings_Repository extends CrudRepository<AdminSettings,Integer > {
    List<AdminSettings> findAll();
    AdminSettings findByGebiet(String gebiet);
}
