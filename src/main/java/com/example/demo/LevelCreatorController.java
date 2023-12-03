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

public class LevelCreatorController {

    @FXML
    private Button intervalsRecognitionButton;

    @FXML
    private Button noteRecognitionButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label levelsCreatorLabel;

    @FXML
    void notesRecognitionOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-level-notes-view.fxml"));
        Parent root = loader.load();

        // Create a new stage with a fixed size
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 700, 500)); // Set a fixed size for the scene

        // Close the current stage (Main Menu)
        Stage currentStage = (Stage) noteRecognitionButton.getScene().getWindow();
        currentStage.close();

        // Show the LevelCreator stage
        stage.show();
    }

    @FXML
    void intervalsRecognitionOnClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("create-level-intervals-view.fxml"));
        Parent root = loader.load();

        // Create a new stage with a fixed size
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 700, 500)); // Set a fixed size for the scene

        // Close the current stage (Main Menu)
        Stage currentStage = (Stage) intervalsRecognitionButton.getScene().getWindow();
        currentStage.close();

        // Show the LevelCreator stage
        stage.show();

    }

    @FXML
    void backToMenuOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);

    }

}