package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void initialize() {
        welcomeText.setText("WYLOGUJ");
    }

    @FXML
    protected void onExitButtonClick() {
        // Exit the application
        Platform.exit();
    }

    @FXML
    private void onBackToMenuButtonClick() {
        // Code to navigate back to the main menu
        HelloApplication helloApplication = new HelloApplication();
        try {
            helloApplication.start(new Stage());

            // Close the current stage (Stats page)
            Stage currentStage = (Stage) welcomeText.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
