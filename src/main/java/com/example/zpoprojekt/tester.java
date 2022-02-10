package com.example.zpoprojekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Objects;

public class tester {
    public static void main(String[] args) throws IOException {
        OpenSkyArrivalDeparture openSkyArrivalDeparture = new OpenSkyArrivalDeparture();
        FlightData[] flightData = openSkyArrivalDeparture.getDepartures("EDDF", 1517227200, 1517230800);
        ObservableList<FlightData> flightData1 = FXCollections.observableArrayList();

        for (int i = 0; i < flightData.length; i++){
            flightData1.add(flightData[i]);
        }
        System.out.println(flightData1);
    }
}
