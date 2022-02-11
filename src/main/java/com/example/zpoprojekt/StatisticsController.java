package com.example.zpoprojekt;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The class that controls StatisticsView.fxml
 */
public class StatisticsController extends SearchingViewController {

    protected ObservableList<FlightData> flightDataList = FXCollections.observableArrayList();

    private FlightData[] flightData;

    private final OpenSkyNetwork openSkyNetwork = new OpenSkyNetwork();

    private String[] options = {"Departures", "Arrivals"};

    @FXML
    private TextField airportEDX;

    @FXML
    private TextField beginTimeEDX;

    @FXML
    private TextField endTimeEDX;

    @FXML
    private ChoiceBox<String> selectBox;

    @FXML
    private CategoryAxis aircraftColumn;

    @FXML
    private Button resetButton;

    @FXML
    private BarChart<?, ?> statisticsPlot;

    @FXML
    private NumberAxis timeColumn;


    /**
     * The method responsible for drawing the graph when the drawButton is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void drawButtonClicked(ActionEvent event) throws IOException {
        try {
            if (airportEDX.getText() != null && beginTimeEDX.getText() != null && endTimeEDX.getText() != null) {
                    String icao = airportEDX.getText();
                    long unixBeginTime = unixTime(beginTimeEDX.getText());
                    long unixEndTime = unixTime(endTimeEDX.getText());
                    if(selectBox.getValue().equals("Departures")){
                        flightData = openSkyNetwork.getDepartures(icao, unixBeginTime, unixEndTime);
                    }
                    else if(selectBox.getValue().equals("Arrivals")){
                        flightData = openSkyNetwork.getArrival(icao, unixBeginTime, unixEndTime);
                    }
                    else {
                        alertLoader("Arrivals/Departures error", "Choose direction!");
                    }
                    if (flightData == null) {
                        alertLoader("Flights Data Error", "There were no flights at this time");
                    }
                    flightDataList.addAll(Arrays.asList(flightData));
                    flightDataList.removeIf(x -> x.getMinutes() < 0 | x.getMinutes() > 1000);
                    XYChart.Series series = new XYChart.Series();
                    for (int i = 0; i < flightDataList.size(); i++) {
                        series.getData().add(new XYChart.Data(flightDataList.get(i).getIcao24(), flightDataList.get(i).getMinutes()));
                    }
                    statisticsPlot.getData().add(series);
            } else {
                System.out.println("XD");
                alertLoader("Empty data error", "Fields with airport and datetime cannot be empty");
            }
        } catch (DateTimeParseException e) {
            System.out.println("XD");
            alertLoader("Empty data error", "Fields with airport and datetime cannot be empty");
        }

    }

    /**
     * The method that close Statistics.fxml and returns to StartingMenu.fxml
     * @param event
     */
    @FXML
    void returnButtonClicked(ActionEvent event) {
        windowOpen("StartingMenu.fxml");
        Stage stage = (Stage) resetButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method that reset the BarChart.
     * @param event
     */
    @FXML
    void resetButtonClicked(ActionEvent event) {
        Collections.singleton(statisticsPlot.getData().setAll());
    }

    /**
     * The method that initialize when statistics.fxml is opened that adds data selectBox
     */
    @FXML
    void initialize() {
        assert aircraftColumn != null : "fx:id=\"icao24Column\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert statisticsPlot != null : "fx:id=\"statisticsPlot\" was not injected: check your FXML file 'Statistics.fxml'.";
        assert timeColumn != null : "fx:id=\"timeColumn\" was not injected: check your FXML file 'Statistics.fxml'.";
        selectBox.getItems().addAll(options);
    }

    /**
     * The methods that convert date format to UnixTime format
     * @param fullTime - String
     * @return time in UnixTime format
     */
    public long unixTime(String fullTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime beginDate = LocalDateTime.parse(fullTime, dateTimeFormatter);
        System.out.println(beginDate);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedFullDateTime = ZonedDateTime.of(beginDate, zone);
        return zonedFullDateTime.toEpochSecond();
    }
}
