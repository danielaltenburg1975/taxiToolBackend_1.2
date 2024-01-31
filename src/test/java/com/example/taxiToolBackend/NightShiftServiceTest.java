package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.Time_Recording;
import com.example.taxiToolBackend.repository.Time_RecordingRepository;
import com.example.taxiToolBackend.service.NightShift_Service;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

public class NightShiftServiceTest {

    @Mock
    private Time_RecordingRepository timeRecordingRepository;

    @InjectMocks
    private NightShift_Service nightShiftService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMidnightChecker() throws InterruptedException {
        // Mock für den ZonedDateTime.now() aufrufen, um die Zeit zu steuern
        ZonedDateTime mockDate = ZonedDateTime.parse("2024-01-23T10:00:00Z");
        ZonedDateTime midnightDate = ZonedDateTime.parse("2024-01-24T00:00:00Z");
        ZonedDateTime midnightDateAfter = ZonedDateTime.parse("2024-01-24T00:00:01Z");

        // Mock für die getCurrentTime-Methode erstellen
        NightShift_Service nightShiftServiceSpy = spy(nightShiftService);
        doReturn(mockDate).when(nightShiftServiceSpy).getCurrentTime();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        when(timeRecordingRepository.save(any())).thenReturn(new Time_Recording());

        // Test ausführen
        nightShiftServiceSpy.midnightChecker();

        // Warten bis der Scheduler abgelaufen ist
        scheduler.awaitTermination(2, TimeUnit.SECONDS);

        // Überprüfen, ob die setAutomaticStamp-Methode aufgerufen wurde
        verify(timeRecordingRepository, times(2)).save(any());

        // Test aufräumen
        scheduler.shutdownNow();
    }

    // Weitere Tests können hinzugefügt werden, um die anderen Methoden zu überprüfen

    // Hier die neue Methode hinzufügen
    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }
}


