package com.example.taxiToolBackend.restController;



import com.example.taxiToolBackend.data.Customers;
import com.example.taxiToolBackend.data.Time_Recording;
import com.example.taxiToolBackend.repository.Customer_Repository;
import com.example.taxiToolBackend.repository.Time_RecordingRepository;
import com.example.taxiToolBackend.service.NightShift_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The employee area has endpoints that only affect the employee.
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-01-24
 */

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EmployeeArea_Controller {

    public final Customer_Repository customerRepository;
    public final Time_RecordingRepository timeRecordingRepository;
    NightShift_Service nightShiftService;

    @Autowired
    public EmployeeArea_Controller(Customer_Repository customerRepository,Time_RecordingRepository timeRecordingRepository,
                                   NightShift_Service nightShiftService) {
        this.customerRepository = customerRepository;
        this.timeRecordingRepository = timeRecordingRepository;
        this.nightShiftService = nightShiftService;

    }
    // Customer data is provided here
    @GetMapping("/searchCustomerData")
    public Iterable<Customers> getCustomerData(){

        return customerRepository.findAll();
    }

    /**The existing bookings that have not been booked are listed here.
     * Only the admin has read permission.
     */
    @GetMapping("/searchTaxiStamp")
    public Iterable<Time_Recording> getTimeRecording() {

        return timeRecordingRepository.findAll();
    }

    //Setting a new time booking. Only the employee ID number is requested as an argument.
    @PostMapping("/setTaxiStamp")
    public Time_Recording postTaxiStamp(@RequestBody Time_Recording staffID){

        // Set time booking and update status
        staffID.setZeitbuchung(updateZeitbuchung());
        //The entry should always be NO when an entry is created
        staffID.setGebucht("NEIN");
        // Check for night shift based on entries
        checkEntries(staffID.getPersonalnummer());

        return timeRecordingRepository.save(staffID);
    }

    //Sets the current time and date
    public String updateZeitbuchung() {

        ZonedDateTime actDate = ZonedDateTime.now();
        DateTimeFormatter shortFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        return actDate.format(shortFormat);
    }

    //This checks whether or how many entries already exist.
    private void checkEntries(String tempPersonalNummer){
        long entries = countEntriesOfDay(tempPersonalNummer);

        // if there is no entry yet, check whether it is a night shift.
        // Attention ! stamp on always has the even number
        if (entries % 2 == 0) {
            nightShiftService.isNightShift(tempPersonalNummer);
        }

    }
    // Count the entries by date and personnel number
    private long countEntriesOfDay(String tempPersonalnummer) {

        // Set the area
        ZonedDateTime startOfDay = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0);
        ZonedDateTime endOfDay = ZonedDateTime.now().withHour(23).withMinute(59).withSecond(59);

        // Get the current date and put it in the correct format
        DateTimeFormatter customFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        String formattedStartOfDay = startOfDay.format(customFormatter);
        String formattedEndOfDay = endOfDay.format(customFormatter);

        return timeRecordingRepository.countEntries(tempPersonalnummer, formattedStartOfDay, formattedEndOfDay);


    }
}
