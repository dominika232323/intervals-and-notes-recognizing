package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class HistoriaController {
    @FXML
    private Label historyLabel;

//    @FXML
//    protected void initialize() {
//        if (historyLabel != null) {
//            historyLabel.setText("Historia gier");
//        }
//    }

    @FXML
    private void onBackToMenuButtonClick(ActionEvent event) throws IOException {
        SharedFunctionsController menuButton = new SharedFunctionsController();
        menuButton.onBackToMenuButtonClick(event);
    }
    // Add other methods for handling actions in the "stats_view.fxml" scene
}
