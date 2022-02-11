package com.example.zpoprojekt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The class that controls StartingMenu.fxml
 */
public class BeginMenuController {

    @FXML
    private Button searchButton;

    @FXML
    private Button checkButton;

    @FXML
    private Button statisticsButton;

    /**
     * The method that opens SearchingView.fxml and close current fxml file
     * @param event
     */
    @FXML
    void searchButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource("SearchingView.fxml"));
            SearchingViewController.loader(loader, searchButton);
        } catch (Exception error) {
            error.printStackTrace();
            error.getCause();
        }
    }

    /**
     * The method that opens InformationView.fxml and closes the current fxml file
     * @param event
     */
    @FXML
    void checkButtonClicked(ActionEvent event){
        windowOpen("InformationView.fxml");
        Stage stage = (Stage) checkButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method that opens Statistics.fxml and closes the current fxml file
     * @param event
     */
    @FXML
    void statisticsButtonClicked(ActionEvent event){
        windowOpen("Statistics.fxml");
        Stage stage = (Stage) statisticsButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method that initialize when StartingMenu.fxml
     */
    @FXML
    void initialize() {
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'StartingMenu.fxml'.";
        assert checkButton != null : "fx:id=\"trackButton\" was not injected: check your FXML file 'StartingMenu.fxml'.";

    }

    /**
     * The method that opens fxml files.
     * @param view
     */
    public void windowOpen(String view){
        try {
            FXMLLoader loader = new FXMLLoader(BeginMenu.class.getResource(view));
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
}
