package com.example.zpoprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BeginMenuController {

    @FXML
    private Button searchButton;

    @FXML
    private Button trackButton;

    @FXML
    void searchButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("SearchingView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            Stage stageClose = (Stage) searchButton.getScene().getWindow();
            stageClose.close();
        } catch (Exception error) {
            error.printStackTrace();
            error.getCause();
        }
    }

    @FXML
    void trackButtonAction(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("TrackingView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception error) {
            error.printStackTrace();
            error.getCause();
        }
    }

    @FXML
    void initialize() {
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'StartingMenu.fxml'.";
        assert trackButton != null : "fx:id=\"trackButton\" was not injected: check your FXML file 'StartingMenu.fxml'.";

    }

}
