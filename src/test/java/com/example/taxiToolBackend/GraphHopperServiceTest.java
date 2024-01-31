package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.AdminSettings;
import com.example.taxiToolBackend.repository.AdminSettings_Repository;
import com.example.taxiToolBackend.tripServices.GraphHopperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class GraphHopperServiceTest {

    @Mock
    private AdminSettings_Repository adminSettingsRepository;

    @InjectMocks
    private GraphHopperService graphHopperService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculateTime() {

        //value in milliseconds
        graphHopperService.tripTime = 69 *60000; //For testing, it is easier to enter the number of minutes and multiply by 60000.

        // Performing the test argument= time, minute, minute
        String result = graphHopperService.calulateTime("12:01", "10", "20");
        // Verifying the result
        assertEquals("13:40", result);
    }

    @Test
    void testCalculateTrip() {
        // Mocking adminSettingsRepository for findByGebiet method
        AdminSettings adminSettings = new AdminSettings();
        adminSettings.setKilometerpreis("0.5");
        adminSettings.setGrundgebuer("5.0");
        adminSettings.setAnfahrt("2.0");
        adminSettings.setGrossraum("3.0");
        when(adminSettingsRepository.findByGebiet("testGebiet")).thenReturn(adminSettings);

        // Performing the test
        String result = graphHopperService.calculateTrip("testGebiet", "J", "J");

        // Verifying the result
        assertEquals("10.0", result);
    }
}


