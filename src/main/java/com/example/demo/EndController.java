package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class EndController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void initialize() {
        welcomeText.setText("WYLOGUJ");
    }

    @FXML
    protected void onLogOutButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController logOutButton = new SharedFunctionsController();
        logOutButton.onLogOutButtonClick(event);
    }

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
