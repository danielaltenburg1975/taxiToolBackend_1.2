package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.AdminSettings;
import com.example.taxiToolBackend.repository.AdminSettings_Repository;
import com.example.taxiToolBackend.restController.AdminSettings_Controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminSettingsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdminSettings_Repository adminSettingsRepository;

    @InjectMocks
    private AdminSettings_Controller adminSettingsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adminSettingsController).build();
    }

    @Test
    public void testGetAdminSettingsById() throws Exception {
        // Test for getAdminSettingsById
        String gebiet = "Berlin";
        AdminSettings adminSettings = new AdminSettings();
        adminSettings.setGebiet(gebiet);

        when(adminSettingsRepository.findByGebiet(gebiet)).thenReturn(adminSettings);

        mockMvc.perform(get("/getTripInfo/{gebiet}", gebiet))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gebiet").value(gebiet));

        verify(adminSettingsRepository, times(1)).findByGebiet(gebiet);
        verifyNoMoreInteractions(adminSettingsRepository);
    }

    @Test
    public void testGetAdminSettingsByIdNotFound() throws Exception {
        // Test for getAdminSettingsByIdNotFound
        String gebiet = "NonExistentGebiet";

        when(adminSettingsRepository.findByGebiet(gebiet)).thenReturn(null);

        mockMvc.perform(get("/getTripInfo/{gebiet}", gebiet))
                .andExpect(status().isNotFound());

        verify(adminSettingsRepository, times(1)).findByGebiet(gebiet);
        verifyNoMoreInteractions(adminSettingsRepository);
    }

    @Test
    public void testUpdateAdminSettings() throws Exception {
        // Test for updateAdminSettings
        String gebiet = "Berlin";
        AdminSettings existingSettings = new AdminSettings();
        existingSettings.setGebiet(gebiet);

        AdminSettings updatedSettings = new AdminSettings();
        updatedSettings.setGebiet("Berlin");
        updatedSettings.setGrundgebuer("4.50");
        updatedSettings.setKilometerpreis("2.40");
        updatedSettings.setAnfahrt("10.00");
        updatedSettings.setGrossraum("10.00");

        when(adminSettingsRepository.findByGebiet(gebiet)).thenReturn(existingSettings);
        when(adminSettingsRepository.save(existingSettings)).thenReturn(existingSettings);

        mockMvc.perform(put("/updateTripInfo/{gebiet}", gebiet)
                        .content(asJsonString(updatedSettings))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gebiet").value(updatedSettings.getGebiet()));

        verify(adminSettingsRepository, times(1)).findByGebiet(gebiet);
        verify(adminSettingsRepository, times(1)).save(existingSettings);
        verifyNoMoreInteractions(adminSettingsRepository);
    }

    // Utility method to convert an object to a JSON string
    private String asJsonString(Object obj) throws Exception {
        return new ObjectMapper().writeValueAsString(obj);
    }
}