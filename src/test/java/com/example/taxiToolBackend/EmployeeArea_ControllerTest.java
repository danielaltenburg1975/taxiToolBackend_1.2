package com.example.taxiToolBackend;



import com.example.taxiToolBackend.data.Time_Recording;
import com.example.taxiToolBackend.repository.Customer_Repository;
import com.example.taxiToolBackend.repository.Time_RecordingRepository;
import com.example.taxiToolBackend.restController.EmployeeArea_Controller;
import com.example.taxiToolBackend.service.NightShift_Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeArea_ControllerTest {

    @Mock
    private Customer_Repository customerRepository;

    @Mock
    private Time_RecordingRepository timeRecordingRepository;

    @Mock
    private NightShift_Service nightShiftService;

    @InjectMocks
    private EmployeeArea_Controller employeeAreaController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeAreaController).build();
    }

    @Test
    public void testGetCustomerData() throws Exception {
        // Mocking the behavior of customerRepository
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        // Perform the request and assert the result
        MvcResult result = mockMvc.perform(get("/searchCustomerData")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = result.getResponse().getContentAsString();
        System.out.println("Actual Response: " + actualResponse);

        // Verify that customerRepository.findAll() was called
        verify(customerRepository).findAll();
    }



    @Test
    public void testPostTaxiStamp() throws Exception {
        // Mocking the behavior of timeRecordingRepository.save()
        when(timeRecordingRepository.save(any(Time_Recording.class))).thenReturn(new Time_Recording());

        // Mocking the behavior of nightShiftService.isNightShift()
        doNothing().when(nightShiftService).isNightShift(anyString());

        // Perform the request and assert the result
        mockMvc.perform(post("/setTaxiStamp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"personalnummer\": \"1234\"}"))
                .andExpect(status().isOk());

        // Verify that timeRecordingRepository.save() was called
        verify(timeRecordingRepository).save(any(Time_Recording.class));

        // Verify that nightShiftService.isNightShift() was called
        verify(nightShiftService).isNightShift(anyString());
    }
}
