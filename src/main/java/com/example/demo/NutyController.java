package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
        sharedFunctionsController menuButton = new sharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
