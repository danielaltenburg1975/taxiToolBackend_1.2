package com.example.taxiToolBackend.dataRequest;


import lombok.Data;

@Data
public class RouteRequest {
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;

    private String requestAnfahrt;
    private String requestGrossraum;
    private String requestGebiet;

}
