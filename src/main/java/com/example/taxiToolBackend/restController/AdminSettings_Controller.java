package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.AdminSettings;
import com.example.taxiToolBackend.repository.AdminSettings_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
 * Controller class for editing the area settings. The individual prices for the territories are recorded and edited here.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 20.01.2023
 */


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class AdminSettings_Controller {
    private final AdminSettings_Repository adminSettingsRepository;
    @Autowired
    public AdminSettings_Controller(AdminSettings_Repository adminSettingsRepository){
        this.adminSettingsRepository = adminSettingsRepository;
    }

    //Retrieve a list of all AdminSettings.
    @GetMapping("/getAllTripInfo")
    public ResponseEntity<List<AdminSettings>> getAllAdminSettings() {

        List<AdminSettings> allSettings = adminSettingsRepository.findAll();

        if (!allSettings.isEmpty()) {
            return ResponseEntity.ok(allSettings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Retrieve AdminSettings by area
    @GetMapping(value = "/getTripInfo/{gebiet}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminSettings> getAdminSettingsById(@PathVariable String gebiet) {
        AdminSettings adminSettings = adminSettingsRepository.findByGebiet(gebiet);

        if (adminSettings != null) {
            return ResponseEntity.ok(adminSettings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/updateTripInfo/{gebiet}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminSettings> updateAdminSettings(
            @PathVariable String gebiet,
            @Validated @RequestBody AdminSettings updatedSettings
    ) {
        // Find existing AdminSettings by gebiet
        AdminSettings adminSettings = adminSettingsRepository.findByGebiet(gebiet);

        if (adminSettings != null) {
            // Update relevant fields with the new values
            adminSettings.setGebiet(updatedSettings.getGebiet());
            adminSettings.setGrundgebuer(updatedSettings.getGrundgebuer());
            adminSettings.setKilometerpreis(updatedSettings.getKilometerpreis());
            adminSettings.setAnfahrt(updatedSettings.getAnfahrt());
            adminSettings.setGrossraum(updatedSettings.getGrossraum());

            // Save the updated entity back to the repository
            adminSettingsRepository.save(adminSettings);

            return ResponseEntity.ok(adminSettings);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create new areas
    @PostMapping("/createTripInfo")
    public ResponseEntity<AdminSettings> createAdminSettings(@Validated @RequestBody AdminSettings newSettings) {

        // Check if settings already exist for the specified Gebiet
        AdminSettings existingSettings = adminSettingsRepository.findByGebiet(newSettings.getGebiet());

        if (existingSettings != null) {
            // If settings for the area already exist, consider it a conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            // Save Settings
            AdminSettings savedSettings = adminSettingsRepository.save(newSettings);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSettings);
        }
    }


}
