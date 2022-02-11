package com.example.zpoprojekt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The class that handling data retrieved from the server
 */
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

    /**
     * The method that converts UnixTime format to date format
     * @return String flight time of the plane
     */
    public String getFlightTime(){
        long timeFlight = lastSeen - firstSeen;
        int day = (int) TimeUnit.SECONDS.toDays(timeFlight);
        long hours = TimeUnit.SECONDS.toHours(timeFlight) -
                TimeUnit.DAYS.toHours(day);
        long minute = TimeUnit.SECONDS.toMinutes(timeFlight) -
                TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(timeFlight));
        long second = TimeUnit.SECONDS.toSeconds(timeFlight) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(timeFlight));
        return "Hours " + hours + " Min " + minute + " Sec " + second;
    }

    /**
     * The method that converts UnixTime format to date format
     * @return long flight time in minutes
     */
    public long getMinutes(){
        long seconds = lastSeen - firstSeen;
        return TimeUnit.SECONDS.toMinutes(seconds);
    }

    /**
     * The method that converts UnixTime format to date format
     * @return String estimated time of departure for the flight as Unix time
     */
    public String getFirstData(){
        long timestamp = Long.parseLong(String.valueOf(firstSeen));
        Date expiry = new Date(timestamp * 1000);
        return String.valueOf(expiry).substring(3,19);
    }
    /**
     * The method that converts UnixTime format to date format
     * @return String estimated time of arrival for the flight as Unix time
     */
    public String getLastData(){
        long timestamp = Long.parseLong(String.valueOf(lastSeen));
        Date expiry = new Date(timestamp * 1000);
        return String.valueOf(expiry).substring(3,19);
    }

}
