package com.example.zpoprojekt;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchingViewController {

    ObservableList<FlightData> flightDataList = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField airportEDX;

    @FXML
    private TableColumn<FlightData, String> arrivalColumn;

    @FXML
    private RadioButton arrivalsButton;

    @FXML
    private Button confirmButton;

    @FXML
    private TableColumn<FlightData, String> departureColumn;

    @FXML
    private RadioButton departuresButton;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField endTimeEDX;

    @FXML
    private TableColumn<FlightData, Long> flightLengthColumn;

    @FXML
    private TableColumn<FlightData, Long> flightTimeColumn;

    @FXML
    private TableView<FlightData> flights;

    @FXML
    private TableColumn<FlightData, String> icao24Column;

    @FXML
    private Button returnButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField startTimeEDX;

    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException {
        if (startTimeEDX.getText() != null && endTimeEDX != null && startDate.getValue() != null && endDate.getValue() != null) {
            if (arrivalsButton.isSelected()) {
                OpenSkyArrivalDeparture openSkyArrivalDeparture = new OpenSkyArrivalDeparture();
                String icao = airportEDX.getText().toUpperCase();
                long unixBeginTime = unixTime(startDate.getValue().toString(), startTimeEDX.getText());
                long unixEndTime = unixTime(startDate.getValue().toString(), endTimeEDX.getText());
                FlightData[] flightData = openSkyArrivalDeparture.getArrival(icao, unixBeginTime, unixEndTime);


                for (int i = 0; i < flightData.length; i++) {
                    flightDataList.add(flightData[i]);
                }
                flights.setItems(flightDataList);
                loadData();
            } else if (departuresButton.isSelected()) {

            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection error");
            alert.setContentText("Fields with date and time cannot be empty");
        }
    }

    @FXML
    void returnButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("StartingMenu.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Stage stageClose = (Stage) returnButton.getScene().getWindow();
        stageClose.close();
    }

    @FXML
    void initialize() {
        assert airportEDX != null : "fx:id=\"airportEDX\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert arrivalColumn != null : "fx:id=\"arrivalColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert arrivalsButton != null : "fx:id=\"arrivalsButton\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert confirmButton != null : "fx:id=\"confirmButton\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert departureColumn != null : "fx:id=\"departureColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert departuresButton != null : "fx:id=\"departuresButton\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert endDate != null : "fx:id=\"endDate\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert endTimeEDX != null : "fx:id=\"endTimeEDX\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert flightLengthColumn != null : "fx:id=\"flightLengthColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert flightTimeColumn != null : "fx:id=\"flightTimeColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert flights != null : "fx:id=\"flights\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert icao24Column != null : "fx:id=\"icao24Column\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert startDate != null : "fx:id=\"startDate\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert startTimeEDX != null : "fx:id=\"startTimeEDX\" was not injected: check your FXML file 'SearchingView.fxml'.";
        ToggleGroup toggleGroup = new ToggleGroup();
        departuresButton.setToggleGroup(toggleGroup);
        arrivalsButton.setToggleGroup(toggleGroup);
    }

    private void loadData() {
        icao24Column.setCellValueFactory(new PropertyValueFactory<>("icao24"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("estDepartureAirport"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("estArrivalAirport"));
        flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>(""));
        flightLengthColumn.setCellValueFactory(new PropertyValueFactory<>(""));
    }

    private long unixTime(String date, String time) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fullTime = date + " " + time;

        LocalDateTime beginDate = LocalDateTime.parse(fullTime, dateTimeFormatter);

        System.out.println(beginDate);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedFullDateTime = ZonedDateTime.of(beginDate, zone);

        long unixFullDateTime = zonedFullDateTime.toEpochSecond();

        return unixFullDateTime;
    }

}


