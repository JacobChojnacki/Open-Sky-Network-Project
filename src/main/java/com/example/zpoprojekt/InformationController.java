package com.example.zpoprojekt;

import java.io.IOException;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The class that controls SearchingView.fxml
 */
public class InformationController extends SearchingViewController {

    ObservableList<FlightData> aircraftDataList = FXCollections.observableArrayList();

    @FXML
    private TextField aircraftEDX;

    @FXML
    private TableColumn<FlightData, String> arrivalColumn;

    @FXML
    private TableColumn<FlightData, String> callSignColumn;

    @FXML
    private Button confirmButton;

    @FXML
    private TableColumn<FlightData, String> departureColumn;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField endTimeEDX;

    @FXML
    private TableColumn<FlightData, String> firstSeenColumn;

    @FXML
    private TableView<FlightData> flights;

    @FXML
    private TableColumn<FlightData, Long> lastSeenColumn;

    @FXML
    private Button returnButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField startTimeEDX;


    /**
     * The method that returns information about aircraft and adds data to tableView when confirm button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException {
        flights.getItems().clear();
        OpenSkyNetwork openSkyNetwork = new OpenSkyNetwork();
        if (startTimeEDX.getText() != null && endTimeEDX != null && startDate.getValue() != null && endDate.getValue() != null && aircraftEDX.getText() != null) {
            String icao = aircraftEDX.getText();
            long unixBeginTime = unixTime(startDate.getValue().toString(), startTimeEDX.getText());
            long unixEndTime = unixTime(endDate.getValue().toString(), endTimeEDX.getText());
            FlightData[] aircraftData = openSkyNetwork.getAircraft(icao, unixBeginTime, unixEndTime);
            if (aircraftData == null) {
                alertLoader("Flights Data Error", "There were no flights at this time");
            }
            assert aircraftData != null;
            aircraftDataList.addAll(Arrays.asList(aircraftData));
            flights.setItems(aircraftDataList);
            loadData();
        }
        else{
            alertLoader("Selection error", "Fields with aircraft, date and time cannot be empty");
        }
    }

    /**
     * The method that close InformationView.fxml and opens StartingMenu.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    void returnButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("StartingMenu.fxml"));
        loader(loader, returnButton);
    }

    /**
     * The method that initialize when statistics.fxml is opened that disable future data in datePicker
     */
    @FXML
    void initialize() {
        assert aircraftEDX != null : "fx:id=\"aircraftEDX\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert arrivalColumn != null : "fx:id=\"arrivalColumn\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert callSignColumn != null : "fx:id=\"callSignColumn\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert departureColumn != null : "fx:id=\"departureColumn\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert endDate != null : "fx:id=\"endDate\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert endTimeEDX != null : "fx:id=\"endTimeEDX\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert firstSeenColumn != null : "fx:id=\"firstSeenColumn\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert flights != null : "fx:id=\"flights\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert lastSeenColumn != null : "fx:id=\"lastSeenColumn\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert startDate != null : "fx:id=\"startDate\" was not injected: check your FXML file 'InformationView.fxml'.";
        assert startTimeEDX != null : "fx:id=\"startTimeEDX\" was not injected: check your FXML file 'InformationView.fxml'.";
        disableFutureDates();
    }

    /**
     * The method that loads requested information
     */
    private void loadData() {
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("estDepartureAirport"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("estArrivalAirport"));
        callSignColumn.setCellValueFactory(new PropertyValueFactory<>("callsign"));
        firstSeenColumn.setCellValueFactory(new PropertyValueFactory<>("FirstData"));
        lastSeenColumn.setCellValueFactory(new PropertyValueFactory<>("LastData"));
    }

}
