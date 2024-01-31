package com.example.taxiToolBackend;


import com.example.taxiToolBackend.data.Trip_Collector;
import com.example.taxiToolBackend.dataRequest.Search_TripRequest;
import com.example.taxiToolBackend.repository.Trip_CollectorRepository;
import com.example.taxiToolBackend.restController.CustomerArea_Controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class CustomerArea_ControllerTest {

    @Mock
    private Trip_CollectorRepository tripCollectorRepository;

    @InjectMocks
    private CustomerArea_Controller customerAreaController;

    @Test
    void searchCustomerTrips() {
        // Initialisiere die Mocks
        MockitoAnnotations.initMocks(this);

        // Erstelle Testdaten
        String customerName = "Daniel Altenburg";
        LocalDateTime now = LocalDateTime.now();
        Trip_Collector trip1 = new Trip_Collector(now.toString(), "Abholort1", "Zielort1", "Anmerkung1", "Personen1", "Auto1", "Fahrer1", "Bezahlung1", null, null);
        Trip_Collector trip2 = new Trip_Collector(now.toString(), "Abholort2", "Zielort2", "Anmerkung2", "Personen2", "Auto2", "Fahrer2", "Bezahlung2", null, null);
        List<Trip_Collector> mockTrips = Arrays.asList(trip1, trip2);

        // Setze das Verhalten der Mocks
        when(tripCollectorRepository.findByCustomerName(customerName)).thenReturn(mockTrips);

        // Führe die Methode aus, die getestet werden soll
        Iterable<Search_TripRequest> result = customerAreaController.searchCustomerTrips(customerName);

        // Überprüfe das Ergebnis
        verify(tripCollectorRepository, times(1)).findByCustomerName(customerName);

        // Hier kannst du weitere Überprüfungen für das Ergebnis durchführen
        // ...

        // Beispiel: Überprüfe, ob die Anzahl der Ergebnisse korrekt ist
        int expectedResultSize = mockTrips.size();
        int actualResultSize = ((List<Search_TripRequest>) result).size();
        assert expectedResultSize == actualResultSize : "Unexpected result size";

        // Beispiel: Überprüfe, ob die Werte korrekt konvertiert wurden
        Search_TripRequest firstResult = ((List<Search_TripRequest>) result).get(0);
        assert firstResult.getFahrgast().equals(customerName) : "Unexpected customer name";
        assert firstResult.getAbholort().equals(trip1.getAbholort()) : "Unexpected pickup location";
        // ...

        // Weitere Überprüfungen hier...

    }
}

