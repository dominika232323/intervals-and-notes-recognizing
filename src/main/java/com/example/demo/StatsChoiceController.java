package com.example.demo;

import javafx.fxml.FXML;
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
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        try {
            helloApplication.start(new Stage());

            // Close the current stage (Stats page)
            Stage currentStage = (Stage) statsLabel.getScene().getWindow();
            currentStage.close();
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
