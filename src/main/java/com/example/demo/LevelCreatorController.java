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
    void notesRecognitionOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController button = new SharedFunctionsController();
        button.changeStage(event, "create-level-notes-view.fxml");
    }

    @FXML
    void intervalsRecognitionOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController button = new SharedFunctionsController();
        button.changeStage(event, "create-level-intervals-view.fxml");
    }

    @FXML
    void backToMenuOnClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);

    }

}