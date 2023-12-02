package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsChoiceController {
    @FXML
    private Label statsLabel;


    @FXML
    protected void initialize() {
        if (statsLabel != null) {
            statsLabel.setText("Statystyki");
        }
    }

    @FXML
    private void onBackToMenuButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) statsLabel.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNotesButtonClick(){
        StatsNotesApplication app = new StatsNotesApplication();
        try
        {
            app.start(new Stage());

            Stage currentStage = (Stage)statsLabel.getScene().getWindow();
            currentStage.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void onIntervalsButtonClick(){

    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
