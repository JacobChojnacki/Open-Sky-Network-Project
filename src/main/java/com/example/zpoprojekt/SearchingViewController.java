package com.example.zpoprojekt;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

/**
 * The class that controlls searchingView.fxml
 */
public class SearchingViewController extends BeginMenuController{

    ObservableList<FlightData> flightDataList = FXCollections.observableArrayList();

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
    private TableColumn<FlightData, String> flightTimeColumn;

    @FXML
    private TableColumn<FlightData, Long> startTimeColumn;

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

    private FlightData[] flightData;

    /**
     * The method that returns information about arrivals/departures and adds data to tableView
     * when confirmButton is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException {
        flights.getItems().clear();
        OpenSkyNetwork openSkyNetwork = new OpenSkyNetwork();
        if (startTimeEDX.getText() != null && endTimeEDX != null && startDate.getValue() != null && endDate.getValue() != null && airportEDX.getText() !=null) {
            String icao = airportEDX.getText().toUpperCase();
            long unixBeginTime = unixTime(startDate.getValue().toString(), startTimeEDX.getText());
            long unixEndTime = unixTime(startDate.getValue().toString(), endTimeEDX.getText());
            if (arrivalsButton.isSelected()) {
                flightData = openSkyNetwork.getArrival(icao, unixBeginTime, unixEndTime);
                if (flightData == null) {
                    alertLoader("Flights Data Error", "There were no flights at this time");
                }
                assert flightData != null;
                flightDataList.addAll(Arrays.asList(flightData));
                flights.setItems(flightDataList);
                loadData();
            } else if (departuresButton.isSelected()) {
                flightData = openSkyNetwork.getDepartures(icao, unixBeginTime, unixEndTime);
                if (flightData == null) {
                    alertLoader("Flights Data Error", "There were no flights at this time");
                }
                assert flightData != null;
                flightDataList.addAll(Arrays.asList(flightData));
                flights.setItems(flightDataList);
                loadData();
            } else {
                alertLoader("Airport error","You have to choose between arrival and departure");
            }
        } else {
            alertLoader("Selection error", "Fields with airport, date and time cannot be empty");
        }
    }

    /**
     * The method that close SearchingMenu.fxml and returns to StartingMenu.fxml
     * when the return button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void returnButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("StartingMenu.fxml"));
        loader(loader, returnButton);
    }

    /**
     * The method that generete alerts
     * @param title String - title of alert
     * @param text String - content of text
     */
    protected void alertLoader(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * The method that loads the fxml files
     * @param loader
     * @param returnButton
     * @throws IOException
     */
    static void loader(FXMLLoader loader, Button returnButton) throws IOException {
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Stage stageClose = (Stage) returnButton.getScene().getWindow();
        stageClose.close();
    }

    /**
     * The method that initialize when statistics.fxml is opened that disable future data in datePicker
     * and creates toggleGroup
     */
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
        assert flightTimeColumn != null : "fx:id=\"flightLengthColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert startTimeColumn != null : "fx:id=\"flightTimeColumn\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert flights != null : "fx:id=\"flights\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert icao24Column != null : "fx:id=\"icao24Column\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert returnButton != null : "fx:id=\"returnButton\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert startDate != null : "fx:id=\"startDate\" was not injected: check your FXML file 'SearchingView.fxml'.";
        assert startTimeEDX != null : "fx:id=\"startTimeEDX\" was not injected: check your FXML file 'SearchingView.fxml'.";
        ToggleGroup toggleGroup = new ToggleGroup();
        departuresButton.setToggleGroup(toggleGroup);
        arrivalsButton.setToggleGroup(toggleGroup);
        disableFutureDates();
    }

    /**
     * The method that loads requested information
     */
    private void loadData() {
        icao24Column.setCellValueFactory(new PropertyValueFactory<>("icao24"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("estDepartureAirport"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("estArrivalAirport"));
        flightTimeColumn.setCellValueFactory(new PropertyValueFactory<>("flightTime"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("firstData"));
    }

    /**
     * The method that converts Date to UnixTime format
     * @param date - String date
     * @param time - String time
     * @return date in UnixTime format
     */
    public long unixTime(String date, String time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fullTime = date + " " + time;
        LocalDateTime beginDate = LocalDateTime.parse(fullTime, dateTimeFormatter);
        System.out.println(beginDate);
        ZoneId zone = ZoneId.of("Europe/Warsaw");
        ZonedDateTime zonedFullDateTime = ZonedDateTime.of(beginDate, zone);
        return zonedFullDateTime.toEpochSecond();
    }

    /**
     * The method that disable future dates from date picker
     */
    public void disableFutureDates(){
        startDate.setDayCellFactory(x -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
        endDate.setDayCellFactory(x -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }
}


