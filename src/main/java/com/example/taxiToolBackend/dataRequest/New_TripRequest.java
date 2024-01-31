package com.example.taxiToolBackend.dataRequest;

import lombok.Data;

@Data
public class New_TripRequest {
    private String zeit;
    private String fahrgast;
    private String abholort;
    private String zielort;
    private String anmerkung;
    private String fahrer;
    private String gebucht;

}
