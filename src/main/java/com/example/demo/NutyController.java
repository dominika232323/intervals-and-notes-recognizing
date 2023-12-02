package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class NutyController {

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label nutyLabel;

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
        System.out.println("Clicking button nuty");
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
