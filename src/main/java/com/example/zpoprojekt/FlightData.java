package com.example.zpoprojekt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FlightData {
    private String icao24;
    private long firstSeen;
    private String estDepartureAirport;
    private long lastSeen;
    private String estArrivalAirport;
    private String callsign;
    private long estDepartureAirportHorizDistance;
    private long estDepartureAirportVertDistance;
    private long estArrivalAirportHorizDistance;
    private long estArrivalAirportVertDistance;
    private long departureAirportCandidatesCount;
    private long arrivalAirportCandidatesCount;


}
