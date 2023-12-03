package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
