package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class HistoriaController {
    @FXML
    private Label historyLabel;

//    @FXML
//    protected void initialize() {
//        if (historyLabel != null) {
//            historyLabel.setText("Historia gier");
//        }
//    }

    @FXML
    private void onBackToMenuButtonClick() {
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        try {
            helloApplication.start(new Stage());

            // Close the current stage (Stats page)
            Stage currentStage = (Stage) historyLabel.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
