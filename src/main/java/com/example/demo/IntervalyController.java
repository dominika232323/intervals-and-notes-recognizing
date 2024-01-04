package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class IntervalyController {

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label interLabel;

    @FXML
    private void onDefaultLevelsButtonClick() {
        // Add logic for the "Poziomy domyślne" button here
    }

    @FXML
    private void onUserLevelsButtonClick() {
        // Add logic for the "Poziomy użytkownika" button here
    }

    @FXML
    private void onBackToMenuButtonClick() {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Menu");
            stage.setScene(new Scene(root, 700, 500));

            Stage currentStage = (Stage) backToMenuButton.getScene().getWindow();
            currentStage.close();

            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void onPlayButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.changeStage(event, "intervals-game-view.fxml");
    }
}
