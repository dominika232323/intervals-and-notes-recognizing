package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void initialize() {
        welcomeText.setText("WYLOGUJ");
    }

    @FXML
    protected void onLogOutButtonClick() {
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

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        sharedFunctionsController menuButton = new sharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
