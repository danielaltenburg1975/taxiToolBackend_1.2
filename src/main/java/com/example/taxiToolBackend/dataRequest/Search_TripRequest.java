package com.example.taxiToolBackend.dataRequest;

import lombok.Data;

@Data
public class Search_TripRequest {
    private String zeit;
    private String fahrgast;
    private String abholort;
    private String zielort;
    private String anmerkung;

    public Search_TripRequest(String fahrgast, String abholort, String zielort, String zeit, String anmerkung) {
        this.fahrgast = fahrgast;
        this.abholort = abholort;
        this.zielort = zielort;
        this.zeit = zeit;
        this.anmerkung = anmerkung;
    }

}
