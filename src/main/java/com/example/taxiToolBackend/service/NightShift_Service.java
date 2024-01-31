package com.example.taxiToolBackend.service;



import com.example.taxiToolBackend.data.Time_Recording;
import com.example.taxiToolBackend.repository.Time_RecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
* This class checks whether the time has been clocked in after 8 p.m. If this is the case,
* it is automatically clocked out at 0:00 and immediately clocked in again to maintain
* the time recording after the date.
*
* @author Daniel Altenburg
* @version 1.0
* @since 2024-01-23
*/

@Service
public class NightShift_Service {
    Time_RecordingRepository timeRecordingRepository;
    private String tempPersonalID;

    @Autowired
    public NightShift_Service(Time_RecordingRepository timeRecordingRepository) {
        this.timeRecordingRepository = timeRecordingRepository;
    }

    // Checks if the night shift is active for a specific employee ID
    public void isNightShift(String tempPersonalID) {
        this.tempPersonalID = tempPersonalID; //set the personalID

        //Get the current date and time
        ZonedDateTime actDate = ZonedDateTime.now();
        //After-shifts usually start after 8 pm.
        ZonedDateTime nightShiftStart = actDate.withHour(20).withMinute(0).withSecond(0);

        // Check if the current time is after the start of the night shift
        boolean isNightShift = actDate.isAfter(nightShiftStart);

        // If the night shift is active, call the midnightChecker method
        if (isNightShift){
            midnightChecker();
        }
    }
    // Checks every second if it's midnight and then performs actions
    public void midnightChecker(){

        // Scheduler that performs the check every second
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.schedule(() -> {
            // Wait until it's midnight
            try {
                while (!isMidnight()) {
                    TimeUnit.SECONDS.sleep(1);
                }

                // Code is executed only if isMidnight() is true
                setAutomaticStamp(); // stamp off
                setAutomaticStamp(); //stamp on
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, TimeUnit.SECONDS);
    }

    // Checks if it's midnight
    public boolean isMidnight() {

        ZonedDateTime actDate = ZonedDateTime.now();
        ZonedDateTime nachtschichtStart = actDate.withHour(0).withMinute(0).withSecond(0);
        return actDate.isAfter(nachtschichtStart);
    }

    // Performs actions to automatically set a stamp
    public void setAutomaticStamp(){

        ZonedDateTime actDate = ZonedDateTime.now();
        DateTimeFormatter shortFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);

        Time_Recording timeRecording = new Time_Recording();
        timeRecording.setPersonalnummer(tempPersonalID);
        timeRecording.setZeitbuchung(actDate.format(shortFormat));
        timeRecording.setGebucht("NEIN");
        timeRecordingRepository.save(timeRecording);
    }

    //This method is only used to test the time zone using the test class
    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }
}
