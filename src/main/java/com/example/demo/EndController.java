package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private void onBackToMenuButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) welcomeText.getScene().getWindow();
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
