package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StatsNotesController {

    private Label statsLabel;

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        sharedFunctionsController menuButton = new sharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
}
