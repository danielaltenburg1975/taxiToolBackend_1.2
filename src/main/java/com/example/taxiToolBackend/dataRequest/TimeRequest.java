package com.example.taxiToolBackend.dataRequest;

import lombok.Data;

@Data
public class TimeRequest {
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;

    private String requestTermin;
    private String requestLaufweg;
    private String requestPuffer;
}
